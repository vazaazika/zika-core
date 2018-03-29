package br.les.opus.test.gamification.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskGroup;
import br.les.opus.gamification.domain.TaskGroupProgression;
import br.les.opus.gamification.repositories.BadgeRepository;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.TaskGroupProgressionRepository;
import br.les.opus.gamification.repositories.TaskGroupRepository;
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
	private TaskGroupRepository tgDao;
	
	private TaskGroup taskGroup;							//TaskGroup creates POI task group id = 1L
	
	
	/*
	 * Creation of a TaskGroupProgression
	 */
	@Before
	public void initializer() {
		taskGroup = new TaskGroup();
		taskGroup.setGivenXp(2);
		taskGroup.setBadge(badgeDao.findOne(4L));
		
		taskGroup = tgDao.save(taskGroup); 
		
		bob = playerDao.findOne(2L);
				
		groupProgression = new TaskGroupProgression();
		
		groupProgression.setPlayer(bob);
		groupProgression.setTaskGroup(taskGroup);
		
		//save the TaskGroupProgression
		groupProgression = tgpDao.save(groupProgression);
	}
	
	@After
	public void restoreDB() {
		tgpDao.delete(groupProgression);
		tgDao.delete(taskGroup);
	}
	
	/*
	 * Testing badges without progressions. It should be equal to 4
	 */
	@Test
	public void findAllWithoutProgressionsTest() {
		Assert.assertEquals(4, badgeDao.findAllWithProgressions(bob).size());
	}
	

}
