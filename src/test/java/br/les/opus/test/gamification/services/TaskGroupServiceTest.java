package br.les.opus.test.gamification.services;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.domain.TaskAssignmentProgression;
import br.les.opus.gamification.domain.TaskGroupProgression;
import br.les.opus.gamification.repositories.PerformedTaskRepository;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.TaskAssignmentProgressionRepository;
import br.les.opus.gamification.repositories.TaskGroupProgressionRepository;
import br.les.opus.gamification.repositories.TaskRepository;
import br.les.opus.gamification.services.TaskGroupService;
import br.les.opus.test.util.DbTestUtil;

public class TaskGroupServiceTest extends DbTestUtil{
	@Autowired
	private TaskGroupService tgService;
	
	@Autowired
	private TaskAssignmentProgressionRepository tapDao;
	
	@Autowired
	private TaskGroupProgressionRepository tgpDao;
	
	@Autowired
	private PerformedTaskRepository performedTaskRepositoryDao;

	@Autowired
	private PlayerRepository playerDao;
	
	@Autowired
	private TaskRepository taskRepositoryDao;
	
	private Player bob;								//Bob id == 1L
	
	private Task task;								//Task id = 2L
	
	//private TaskAssignment taskAssignment;
	
	private PerformedTask performedTask;
	
	
	/*
	 * Creation of a PerformedTask
	 */
	@Before
	public void initializer() {
		bob = playerDao.findOne(1l);
		task = taskRepositoryDao.findOne(2l);
		
		
		//taskAssignment = taDao.findOne(1L);
		
		performedTask = new PerformedTask();
		performedTask.setPlayer(bob);
		performedTask.setTask(task);
		
		performedTask = performedTaskRepositoryDao.save(performedTask);
	}
	
	@After
	public void restoreDB() {
		performedTaskRepositoryDao.delete(performedTask);
	}
	
	/*
	 * After calling trackProgress, two new progression are created.
	 * 
	 * The first one created is a TaskAssignmentProgression.
	 * Since our database already had three TaskAssignmentProgressions, the fourth one is created
	 * 
	 * The second progression created is a TaskGroupProgression.
	 * Since our database had none TaskGroupProgression, the first one is stored in it.
	 * 
	 */
	@Test
	public void trackProgressTest() {
		 
		tgService.trackProgress(performedTask);
		
		Assert.assertEquals(4, tapDao.count());
		Assert.assertEquals(1, tgpDao.count());
		
		//Retrieving all stored progressions
		List<TaskAssignmentProgression> tapList = tapDao.findAll();
		List<TaskGroupProgression> tgpList = tgpDao.findAll();
		
		/*
		 * Removing the last stored progressions to keep the database consistent
		 * and doesn't mess up with other test cases
		 */
		tapDao.delete(tapList.get(tapList.size() - 1));
		tgpDao.delete(tgpList.get(tgpList.size() - 1));
		
	}
	
}
