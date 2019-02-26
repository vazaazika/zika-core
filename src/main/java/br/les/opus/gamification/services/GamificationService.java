package br.les.opus.gamification.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.les.opus.auth.core.domain.Resource;
import br.les.opus.auth.core.domain.Token;
import br.les.opus.auth.core.domain.User;
import br.les.opus.auth.core.services.TokenService;
import br.les.opus.gamification.GamificationException;
import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.TaskRepository;

@Service
public class GamificationService {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private TaskRepository taskDao;

	@Autowired
	private PlayerRepository playerDao;

	@Autowired
	private PerformedTaskService performedTaskService;

	@Autowired
	private TaskGroupService taskGroupService;

	/**
	 * Processes a given request. This method ins the main one in the game module.
	 * The steps executed in this method are:
	 * 1) Identifies the player performing the request;
	 * 2) Identifies the system resource related to the request;
	 * 3) Identifies the task related to the system resource;
	 * 4) Creates the PerformedTask object to register the action taken by the player;
	 * 5) Calls the TaskGroupService to track progress in any possible TaskGroup affected.
	 * 
	 * @param request the request performed by a user
	 */
	public void processRequest(HttpServletRequest request) {
		// get the player who performed the request
		Player player = loadPlayer(request);


		// tries to find the Task related to the resource
		Resource resource = new Resource(request);
		Token token = tokenService.getAuthenticatedUser(request);
		resource = token.matchedSystemResource(resource);
		if (resource == null) {
			return;
		}
		
		Task task = taskDao.findByResource(resource);
		
		if (task == null) {
			return;
		}
		

		PerformedTask performedTask = performedTaskService.register(task, player);
		taskGroupService.trackProgress(performedTask);
		
		//Player updatedPlayer = playerDao.findOne(player.getId());
		

	}
	
	public Player loadPlayer(HttpServletRequest request) {
		Token token = tokenService.getAuthenticatedUser(request);
		User user = token.getUser();
		Player player = playerDao.findOne(user.getId());
		if (player == null) {
			throw new GamificationException("The user " + user + " is not a player.");
		}
		return player;
	}
	
}
