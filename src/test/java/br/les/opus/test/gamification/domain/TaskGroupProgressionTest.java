package br.les.opus.test.gamification.domain;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskAssignment;
import br.les.opus.gamification.domain.TaskGroup;
import br.les.opus.gamification.domain.TaskGroupProgression;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.TaskAssignmentRepository;
import br.les.opus.gamification.repositories.TaskGroupProgressionRepository;
import br.les.opus.test.util.DbTestUtil;

public class TaskGroupProgressionTest extends DbTestUtil{
	
	@Autowired
	private TaskGroupProgressionRepository tgpDao;
	
	private TaskGroupProgression groupProgression;
	
	@Autowired
	private PlayerRepository playerDao;
	
	private Player bob;										//Bob id == 1L
	
	@Autowired
	private TaskAssignmentRepository taskAssignmentDao;		//used to retrieve the taskgroup
	
	private TaskGroup taskGroup;								//TaskGroup create poi task group id = 1L
	
	private TaskAssignment tAssignment;
	
	
	/*
	 * Creation of a TaskGroupProgression
	 */
	@Before
	public void initializer() {
		bob = playerDao.findOne(1L);
		
		tAssignment = taskAssignmentDao.findOne(1L);
		taskGroup = tAssignment.getTaskGroup();
				
		groupProgression = new TaskGroupProgression();
		
		groupProgression.setPlayer(bob);
		groupProgression.setTaskGroup(taskGroup);
		groupProgression.computeProgress(1, 2);
		
		//save the TaskGroupProgression
		groupProgression = tgpDao.save(groupProgression);
	}
	
	@After
	public void restoreDB() {
		tgpDao.delete(groupProgression);
	}
	
	/*
	 * Testing progression incomplete
	 */
	@Test
	public void computeProgressIncompleteTest() {
		tAssignment = taskAssignmentDao.findOne(1L);
		taskGroup = tAssignment.getTaskGroup();
		
		List<TaskGroupProgression> progressions = taskGroup.getProgressions();
		
		//there is only 1 taskgroupprogression
		Assert.assertEquals(1, progressions.size());
		
		
		//verify progression = 0.5 and workload = 1.0
		Assert.assertEquals(new Float(0.5), progressions.get(0).getProgress());
		Assert.assertEquals(new Integer(1), progressions.get(0).getCompletedWork());
	}
	
	/*
	 * Testing progression complete
	 */
	@Test
	public void computeProgressCompleteTest() {
		//create a second taskgroupprogression 
		TaskGroupProgression secondGroupProgression = new TaskGroupProgression();
		
		Player alicia = playerDao.findOne(2L);
		
		secondGroupProgression.setPlayer(alicia);
		secondGroupProgression.setTaskGroup(taskGroup);
		secondGroupProgression.computeProgress(2, 2);
				
		//save the TaskGroupProgression
		secondGroupProgression = tgpDao.save(secondGroupProgression);
		
		
		tAssignment = taskAssignmentDao.findOne(1L);
		taskGroup = tAssignment.getTaskGroup();
		List<TaskGroupProgression> progressions = taskGroup.getProgressions();
		
		//there is only 1 taskgroupprogression
		Assert.assertEquals(2, progressions.size());
		
		
		//verify progression = 1 and workload = 2
		Assert.assertEquals(new Float(1), progressions.get(1).getProgress());
		Assert.assertEquals(new Integer(2), progressions.get(1).getCompletedWork());
		
		tgpDao.delete(secondGroupProgression);
	}
	
}
