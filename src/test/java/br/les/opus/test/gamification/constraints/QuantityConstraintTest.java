package br.les.opus.test.gamification.constraints;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.constraints.QuantityConstraint;
import br.les.opus.gamification.constraints.checkers.QuantityConstraintChecker;
import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.domain.TaskAssignment;
import br.les.opus.gamification.repositories.PerformedTaskRepository;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.QuantityConstraintRepository;
import br.les.opus.gamification.repositories.TaskAssignmentRepository;
import br.les.opus.gamification.repositories.TaskRepository;
import br.les.opus.test.util.DbTestUtil;

public class QuantityConstraintTest extends DbTestUtil{
	@Autowired
	private QuantityConstraintRepository qntDao;
	
	@Autowired
	private TaskAssignmentRepository taDao;
	
	@Autowired
	private QuantityConstraintChecker checker;
	
	@Autowired
	private PerformedTaskRepository performedTaskRepositoryDao;
	
	@Autowired
	private PlayerRepository playerDao;
	
	@Autowired
	private TaskRepository taskRepositoryDao;
	
	private PerformedTask[] performedTask = new PerformedTask[3];

	private Player bob;			//Bob id == 1L
	
	private Task task;			//Task id = 1L
	
	private QuantityConstraint quantityConstraint;	//Boundary = 2
	
	private TaskAssignment taskAssignment;
	
	@Before
	public void initializer() {
		bob = playerDao.findOne(1l);
		task = taskRepositoryDao.findOne(1l);
		
		
		taskAssignment = taDao.findOne(1L);
		
		quantityConstraint = new QuantityConstraint(); 
		quantityConstraint.setDescription("Testing Quantity Constraint == 2");
		quantityConstraint.setQuantity(2);	//quantity constraint == 2
		quantityConstraint.setTaskAssignment(taskAssignment);
		
		quantityConstraint = qntDao.save(quantityConstraint);
		
		
		//First performed Task
		performedTask[0] = new PerformedTask();
		performedTask[0].setPlayer(bob);
		performedTask[0].setTask(task);
		
		
		
		//Second performed Task
		performedTask[1] = new PerformedTask();
		performedTask[1].setPlayer(bob);
		performedTask[1].setTask(task);
		
		
		//Third performed Task
		performedTask[2] = new PerformedTask();
		performedTask[2].setPlayer(bob);
		performedTask[2].setTask(task);
		
		
		}
	
	@After
	public void restoreDB() {
		qntDao.delete(quantityConstraint);
		
		for(PerformedTask pf: performedTask) {
			if(pf.getId() != null) {
				performedTaskRepositoryDao.delete(pf);
			}
			
		}
	}
	
	
	/*
	 * Verify workloads without Constraint
	 */
	@Test
	public void workloadNoConstraintTest() {
		TaskAssignment assignment = taDao.findOne(1L);
		
		//workload should be 1 since there is no constrains
		Assert.assertEquals(new Integer(1), assignment.getWorkload());
	}
	
	/*
	 * Testing workload using the Boundary-value analysis
	 * 
	 * Inferior = 4
	 * Boundary = 5
	 * Superior = 3
	 */
	
	/*
	 * Verify workload with Quantity Constraint (Limit == 5 -> true)
	 */
	@Test
	public void workloadConstraintTest() {
		TaskAssignment assignment = taDao.findOne(5L);
		
		//workload should be 5 since there is a Quantity constrains equal to 5
		Assert.assertEquals(new Integer(5), assignment.getWorkload());
	}
	
	/*
	 * Verify workload with Quantity Constraint (Limit Inferior == 4 -> false)
	 */
	@Test
	public void workloadConstraintInferiorInferiorTest() {
		TaskAssignment assignment = taDao.findOne(5L);
		
		//workload should be 5 since there is a Quantity constrains equal to 5
		Assert.assertNotEquals(new Integer(4), assignment.getWorkload());
	}
	
	/*
	 * Verify workload with Quantity Constraint (Limit Superior == 6 -> false)
	 */
	@Test
	public void workloadConstraintLimitSuperiorTest() {
		TaskAssignment assignment = taDao.findOne(5L);
		
		//workload should be 5 since there is a Quantity constrains equal to 5
		Assert.assertNotEquals(new Integer(6), assignment.getWorkload());
	}
	
	
	
	/*
	 * Testing Quantity constraint using the Boundary-value analysis
	 * 
	 * Inferior = 1
	 * Boundary = 2
	 * Superior = 3
	 */
	
	/*
	 * Verify QuantityConstraint checker Inferior = 1, workload = 1
	 */
	@Test
	public void verifyQuantityConstrainInferiorChecker() {
		performedTask[0] = performedTaskRepositoryDao.save(performedTask[0]);
		
		Assert.assertEquals(new Integer(1), checker.completedWork(quantityConstraint, performedTask[0]));
		
		
	}
	
	/*
	 * Verify QuantityConstraint checker Boundary = 2, workload = 2
	 */
	@Test
	public void verifyQuantityConstrainBoundaryChecker() {
		performedTask[0] = performedTaskRepositoryDao.save(performedTask[0]);
		performedTask[1] = performedTaskRepositoryDao.save(performedTask[1]);
		
		//The value should be 2 since the constraint is satisfied, returning the workload equal to 2 as result 
		Assert.assertEquals(new Integer(2), checker.completedWork(quantityConstraint, performedTask[1]));
		
		
	}
	
	/*
	 * Verify QuantityConstraint checker Superior = 3, workload = 2
	 */
	@Test
	public void verifyQuantityConstrainSuperiorChecker() {
		performedTask[0] = performedTaskRepositoryDao.save(performedTask[0]);
		performedTask[1] = performedTaskRepositoryDao.save(performedTask[1]);
		performedTask[2] = performedTaskRepositoryDao.save(performedTask[2]);
		
		//The value should be 2 since the constraint is satisfied, returning the workload equal to 2 as result 
		Assert.assertEquals(new Integer(2), checker.completedWork(quantityConstraint, performedTask[2]));
		
	}

}
