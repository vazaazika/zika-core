package br.les.opus.test.gamification.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.repositories.PerformedChallengeRepository;
import br.les.opus.gamification.repositories.PerformedTaskRepository;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.TaskRepository;
import br.les.opus.gamification.services.PerformedTaskService;
import br.les.opus.test.util.DbTestUtil;

public class PerformedChallengeServiceTest extends DbTestUtil{
	@Autowired
	private PerformedTaskService ptService;
	
	@Autowired
	private PerformedTaskRepository performedTaskDao;
	
	@Autowired
	private PerformedChallengeRepository pcDao;
	
	@Autowired
	private PlayerRepository playerDao;
	
	@Autowired
	private TaskRepository taskRepositoryDao;
	
	private Player bob;
	private Task task;
	
	@Before
	public void initializer() {
		bob = playerDao.findOne(1l);
		task = taskRepositoryDao.findOne(1l);	
	}
	
	@Test
	public void registerTest() {
		PerformedTask registeredPT = ptService.register(task, bob);
		
		Assert.assertTrue(performedTaskDao.exists(registeredPT.getId()));
		
		Assert.assertEquals(1, pcDao.findIncompletePerformedChallenge().size());

	}


}
