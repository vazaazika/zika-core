package br.les.opus.test.gamification.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskAssignment;
import br.les.opus.gamification.domain.TaskGroup;
import br.les.opus.gamification.domain.TaskGroupProgression;
import br.les.opus.gamification.repositories.BadgeRepository;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.TaskAssignmentRepository;
import br.les.opus.gamification.repositories.TaskGroupProgressionRepository;
import br.les.opus.test.util.DbTestUtil;

public class BadgeRepositoryTest extends DbTestUtil{
	@Autowired
	private BadgeRepository badgeDao;
	
	@Autowired
	private TaskGroupProgressionRepository tgpDao;
	
	private TaskGroupProgression groupProgression;
	
	@Autowired
	private PlayerRepository playerDao;
	
	private Player bob;										//Bob id == 1L
	
	@Autowired
	private TaskAssignmentRepository taskAssignmentDao;		//used to retrieve the taskgroup
	
	private TaskGroup taskGroup;								//TaskGroup create poi task group id = 1L
	
	
	/*
	 * Creation of a TaskGroupProgression
	 */
	@Before
	public void initializer() {
		bob = playerDao.findOne(1L);
		
		TaskAssignment tAssignment = taskAssignmentDao.findOne(1L);
		taskGroup = tAssignment.getTaskGroup();
				
		groupProgression = new TaskGroupProgression();
		
		groupProgression.setPlayer(bob);
		groupProgression.setTaskGroup(taskGroup);
		
		//save the TaskGroupProgression
		groupProgression = tgpDao.save(groupProgression);
	}
	
	@After
	public void restoreDB() {
		tgpDao.delete(groupProgression);
	}
	
	/*
	 * Testing badges without progressions
	 */
	@Test
	public void findAllWithoutProgressionsTest() {
		
		Assert.assertEquals(3, badgeDao.findAllWithProgressions(bob).size());
	}
	

}
