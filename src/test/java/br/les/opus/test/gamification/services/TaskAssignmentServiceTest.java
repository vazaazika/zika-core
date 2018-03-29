package br.les.opus.test.gamification.services;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.constraints.AssignmentConstraint;
import br.les.opus.gamification.constraints.QuantityConstraint;
import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.domain.TaskAssignment;
import br.les.opus.gamification.repositories.PerformedTaskRepository;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.QuantityConstraintRepository;
import br.les.opus.gamification.repositories.TaskAssignmentRepository;
import br.les.opus.gamification.repositories.TaskRepository;
import br.les.opus.gamification.services.TaskAssignmentService;
import br.les.opus.test.util.DbTestUtil;

public class TaskAssignmentServiceTest extends DbTestUtil{
	@Autowired
	private TaskAssignmentService taService;
	
	@Autowired
	private QuantityConstraintRepository qntDao;
	
	@Autowired
	private PerformedTaskRepository performedTaskRepositoryDao;
	
	@Autowired
	private TaskAssignmentRepository taDao;
	
	@Autowired
	private PlayerRepository playerDao;
	
	@Autowired
	private TaskRepository taskRepositoryDao;
	
	private Player bob;								//Bob id == 1L
	
	private Task task;								//Task id = 1L
	
	private TaskAssignment taskAssignment;
	
	private PerformedTask performedTask;
	
	
	/*
	 * Creation of a PerformedTask
	 */
	@Before
	public void initializer() {
		bob = playerDao.findOne(1l);
		task = taskRepositoryDao.findOne(1l);
		
		
		taskAssignment = taDao.findOne(1L);
		
		performedTask = new PerformedTask();
		performedTask.setPlayer(bob);
		performedTask.setTask(task);
		
		performedTask = performedTaskRepositoryDao.save(performedTask);
	}
	
	@After
	public void restoreDB() {
		performedTaskRepositoryDao.delete(performedTask);
		
	}
	
	
	
	@Test
	public void completedWorkWithoutConstraintTest() {
		Assert.assertEquals(new Integer(1), taService.completedWork(performedTask, taskAssignment));

	}
	
	@Test
	public void completedWorkWithConstraintTest() {
		QuantityConstraint quantityConstraint = new QuantityConstraint(); 
		quantityConstraint.setDescription("Testing Quantity Constraint == 2");
		quantityConstraint.setQuantity(2);					//quantity constraint == 2
		quantityConstraint.setTaskAssignment(taskAssignment);
		
		quantityConstraint = qntDao.save(quantityConstraint);
		
		taskAssignment.setConstraints(Arrays.asList((AssignmentConstraint) quantityConstraint));
		
		
		//The value should be 1 since the constraint is equal to 2, but only 1 PerformedTask was executed 
		Assert.assertEquals(new Integer(1), taService.completedWork(performedTask, taskAssignment));

		qntDao.delete(quantityConstraint);
	}


}
