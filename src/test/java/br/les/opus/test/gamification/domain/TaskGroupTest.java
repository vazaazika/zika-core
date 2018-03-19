package br.les.opus.test.gamification.domain;

import java.util.Arrays;

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

public class TaskGroupTest extends DbTestUtil{
	private TaskGroup taskGroup;								//TaskGroup create poi task group id = 1L
	
	@Autowired
	private PlayerRepository playerDao;
	
	@Autowired
	private TaskGroupProgressionRepository tgpDao;
	
	@Autowired
	private TaskAssignmentRepository taskAssignmentDao;		//used to retrieve the taskgroup
	
	private TaskAssignment tAssignment;
	
	private TaskGroupProgression[] groupProgression = new TaskGroupProgression[2];
	
	@Before
	public void initializer() {
		Player bob = playerDao.findOne(1L);
		Player alice = playerDao.findOne(2L);
		
		tAssignment = taskAssignmentDao.findOne(1L);
		taskGroup = tAssignment.getTaskGroup();
		
		
		groupProgression[0] = new TaskGroupProgression();
		groupProgression[0].setPlayer(bob);
		groupProgression[0].setTaskGroup(taskGroup);
		groupProgression[0].computeProgress(1, 2);
		
		groupProgression[1] = new TaskGroupProgression();
		groupProgression[1].setPlayer(alice);
		groupProgression[1].setTaskGroup(taskGroup);
		groupProgression[1].computeProgress(2, 2);
		
		
		//save the TaskGroupProgressions
		groupProgression[0] = tgpDao.save(groupProgression[0]);
		groupProgression[1] = tgpDao.save(groupProgression[1]);
	}
	
	@After
	public void restoreDB() {
		tgpDao.delete(Arrays.asList(groupProgression));
	}
	
	@Test
	public void getWorkloadWithtouConstratinTest() {
		tAssignment = taskAssignmentDao.findOne(1L);
		taskGroup = tAssignment.getTaskGroup();
		
		//there are 2 assignments
		Assert.assertEquals(2, taskGroup.getAssignments().size());
		
		//workload should be 6 (the sum of both assignments)
		Assert.assertEquals(new Integer(6), taskGroup.getWorkload());
	}
	

}
