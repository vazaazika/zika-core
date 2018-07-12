package br.les.opus.test.gamification.constraints;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

import br.les.opus.gamification.constraints.LocationConstraint;
import br.les.opus.gamification.constraints.checkers.LocationConstraintChecker;
import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.domain.TaskAssignment;
import br.les.opus.gamification.repositories.LocationConstraintRepository;
import br.les.opus.gamification.repositories.PerformedTaskRepository;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.TaskAssignmentRepository;
import br.les.opus.gamification.repositories.TaskRepository;
import br.les.opus.test.util.DbTestUtil;

public class LocationConstraintCheckerTest extends DbTestUtil{
	@Autowired
	private TaskAssignmentRepository tarDao;
	
	@Autowired
	private TaskRepository taskRepositoryDao;
	
	@Autowired
	private LocationConstraintRepository locationDao;
	
	@Autowired
	private LocationConstraintChecker checker;
	
	@Autowired
	private PerformedTaskRepository performedTaskRepositoryDao;
	
	@Autowired
	private PlayerRepository playerDao;
	
	private LocationConstraint location;
	
	private Player bob;			//Bob id == 1L
	
	private Task task;			//Task create user task id = 2L
	
	private GeometryFactory geometryFactory;

	
	/*
	 * We should test three performed task:
	 * 1 with location null
	 * 1 with location different from the location constraint
	 * 1 with location equal to the location constraint
	 */
	private PerformedTask performedTask;
	
	@Before
	public void initializer() {
		
		//create and save a location to compose the LocationConstraint object
	    Coordinate c1 = new Coordinate(0.0, 0.0);
	    Coordinate c2 = new Coordinate(3.0, 0.0);
	    Coordinate c3 = new Coordinate(3.0, 3.0);
	    Coordinate c4 = new Coordinate(0.0, 3.0);
	    Coordinate[] coordinates = {c1, c2, c3, c4, c1};
	    
		geometryFactory = new GeometryFactory();

		Polygon polygon = geometryFactory.createPolygon(coordinates);
		TaskAssignment taskAssignment = tarDao.findOne(1L);
		
		if(polygon != null) {
			//create a location constraint to insert in the DB
			location = new LocationConstraint();
			
			location.setGeofence(polygon);
			location.setDescription("Location Constraint testing");
			location.setTaskAssignment(taskAssignment);
			
			location = locationDao.save(location);
		}
		
		bob = playerDao.findOne(1l);
		task = taskRepositoryDao.findOne(2l);
		
	}
	
	@After
	public void restoreDBState() {
		locationDao.delete(location);
	}
	
	@Test
	public void verifyLocationConstraint() {
		LocationConstraint locationConstraint = locationDao.findOne(location.getId());
		
		Assert.assertEquals(location, locationConstraint); //they should be the same object
		
	}
	
	
	/*
	 * Test workload without constraint
	 */
	@Test
	public void getWorkloadTest() {
		performedTask = new PerformedTask();
		performedTask.setPlayer(bob);
		performedTask.setTask(task);
		
		performedTask = performedTaskRepositoryDao.save(performedTask);
		
		
		List<TaskAssignment> taskAssignments  = tarDao.findAllIncomplete(performedTask);
		
		//The number of incomplete task assignments should be 1
		Assert.assertEquals(2, taskAssignments.size());
		
		
		//The workload should be 1 since there is not constraint
		Assert.assertEquals(new Integer(1), taskAssignments.get(0).getWorkload());
		

		performedTaskRepositoryDao.delete(performedTask.getId());
		
		
		
	}
	
	/*
	 * Test LocationConstraintChecker, which PerformedTask location is null
	 */
	@Test
	public void verifyLocationConstrainNullChecker() {
		performedTask = new PerformedTask();
		performedTask.setPlayer(bob);
		performedTask.setTask(task);
		
		performedTask = performedTaskRepositoryDao.save(performedTask);
		
		
		List<TaskAssignment> taskAssignments  = tarDao.findAllIncomplete(performedTask);
		
		//The number of incomplete task assignments should be 1
		Assert.assertEquals(2, taskAssignments.size());
		
		
		//The workload should be 1 since there is not constraint
		Assert.assertEquals(new Integer(0), checker.completedWork(location, performedTask));
		

		performedTaskRepositoryDao.delete(performedTask.getId());
		
		
		
	}
	
	/*
	 * Test LocationConstraintChecker, which PerformedTask location different from null
	 * and different from LocationConstraint
	 */
	@Test
	public void verifyLocationConstrainChecker() {
		performedTask = new PerformedTask();
		performedTask.setPlayer(bob);
		performedTask.setTask(task);
		performedTask.setLocation(geometryFactory.createPoint(new Coordinate(4.0, 4.0)));
		
		performedTask = performedTaskRepositoryDao.save(performedTask);
		
		
		List<TaskAssignment> taskAssignments  = tarDao.findAllIncomplete(performedTask);
		
		//The number of incomplete task assignments should be 1
		Assert.assertEquals(2, taskAssignments.size());
		
		
		//The value should be 0 since location is null
		Assert.assertEquals(new Integer(0), checker.completedWork(location, performedTask));
		

		performedTaskRepositoryDao.delete(performedTask.getId());
		
		
	}
	
	/*
	 * Test LocationConstraintChecker, which PerformedTask location different from null
	 * but inside of LocationConstraint
	 */
	@Test
	public void verifyLocationConstrainEqualChecker() {
		performedTask = new PerformedTask();
		performedTask.setPlayer(bob);
		performedTask.setTask(task);
		performedTask.setLocation(geometryFactory.createPoint(new Coordinate(1.0, 1.0)));
		
		performedTask = performedTaskRepositoryDao.save(performedTask);
		
		
		List<TaskAssignment> taskAssignments  = tarDao.findAllIncomplete(performedTask);
		
		//The number of incomplete task assignments should be 1
		Assert.assertEquals(2, taskAssignments.size());
		
		
		//The value should be 1 since location is within the constraint
		Assert.assertEquals(new Integer(1), checker.completedWork(location, performedTask));
		

		performedTaskRepositoryDao.delete(performedTask.getId());
		
		
	}
	
	/*
	 * Test LocationConstraintChecker, which PerformedTask location different from null
	 * but inside of LocationConstraint
	 */
	@Test
	public void verifyLocationConstrainEqualToPointChecker() {
		performedTask = new PerformedTask();
		performedTask.setPlayer(bob);
		performedTask.setTask(task);
		performedTask.setLocation(geometryFactory.createPoint(new Coordinate(3.0, 3.0)));
		
		performedTask = performedTaskRepositoryDao.save(performedTask);
		
		
		List<TaskAssignment> taskAssignments  = tarDao.findAllIncomplete(performedTask);
		
		//The number of incomplete task assignments should be 1
		Assert.assertEquals(2, taskAssignments.size());
		
		
		//The value should be 0 since location is not within the constraint
		Assert.assertEquals(new Integer(0), checker.completedWork(location, performedTask));
		

		performedTaskRepositoryDao.delete(performedTask.getId());
		
		
	}
	
	
	/*
	 * Test workload with location constraint
	 */
	@Test
	public void getWorkloadConstraintTest() {
		performedTask = new PerformedTask();
		performedTask.setPlayer(bob);
		performedTask.setTask(task);
		performedTask.setLocation(geometryFactory.createPoint(new Coordinate(1.0, 1.0)));
		
		performedTask = performedTaskRepositoryDao.save(performedTask);
		
		
		List<TaskAssignment> taskAssignments  = tarDao.findAllIncomplete(performedTask);
		
		//The number of incomplete task assignments should be 1
		Assert.assertEquals(2, taskAssignments.size());
		
		
		//The workload should be 1 since there is 1 location constraint
		Assert.assertEquals(new Integer(1), taskAssignments.get(0).getWorkload());
		

		performedTaskRepositoryDao.delete(performedTask.getId());
		
		
		
	}

}
