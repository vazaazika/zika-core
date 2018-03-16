package br.les.opus.test.gamification.repositories;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskAssignment;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.TaskAssignmentRepository;
import br.les.opus.gamification.repositories.TaskRepository;
import br.les.opus.test.util.DbTestUtil;

public class TaskAssignmentRepositoryTest extends DbTestUtil {
	
	@Autowired
	private PlayerRepository playerDao;
	
	@Autowired
	private TaskAssignmentRepository taDao;
	
	@Autowired
	private TaskRepository taskDao;
	
	@Test
	public void findAllIncompleteForBobCreatePoiTest() {
		Player bob = playerDao.findOne(1l);
		PerformedTask pTask = new PerformedTask();
		pTask.setPlayer(bob);
		pTask.setTask(taskDao.findOne(1l)); //create poi task
		
		List<TaskAssignment> assignments = taDao.findAllIncomplete(pTask);
		Assert.assertEquals(2, assignments.size());
		
		//it must be the TaskAssignment with id = 1
		Assert.assertEquals(1l, assignments.get(0).getId().longValue()); 
	}
	
	@Test
	public void findAllIncompleteForBobCreateUserTest() {
		Player bob = playerDao.findOne(1l);
		PerformedTask pTask = new PerformedTask();
		pTask.setPlayer(bob);
		pTask.setTask(taskDao.findOne(2l)); //create user task
		
		List<TaskAssignment> assignments = taDao.findAllIncomplete(pTask);
		Assert.assertEquals(1, assignments.size()); 
		
		//it must be the TaskAssignment with id = 4
		Assert.assertEquals(4l, assignments.get(0).getId().longValue()); 
	}
	
	@Test
	public void findAllIncompleteForAliceCreatePoiTest() {
		Player alice = playerDao.findOne(2l);
		PerformedTask pTask = new PerformedTask();
		pTask.setPlayer(alice);
		pTask.setTask(taskDao.findOne(1l)); //create poi task
		
		List<TaskAssignment> assignments = taDao.findAllIncomplete(pTask);
		Assert.assertEquals(3, assignments.size());
	}
	
	@Test
	public void findAllIncompleteForAliceCreateUserTest() {
		Player alice = playerDao.findOne(2l);
		PerformedTask pTask = new PerformedTask();
		pTask.setPlayer(alice);
		pTask.setTask(taskDao.findOne(2l)); //create user task
		
		List<TaskAssignment> assignments = taDao.findAllIncomplete(pTask);
		Assert.assertEquals(2, assignments.size());
	}
}
