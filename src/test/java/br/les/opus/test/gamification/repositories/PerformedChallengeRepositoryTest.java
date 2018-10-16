package br.les.opus.test.gamification.repositories;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.challenge.ChallengeEntity;
import br.les.opus.gamification.domain.challenge.PerformedChallenge;
import br.les.opus.gamification.repositories.ChallengeEntityRepository;
import br.les.opus.gamification.repositories.ChallengeRepository;
import br.les.opus.gamification.repositories.PerformedChallengeRepository;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.test.util.DbTestUtil;

public class PerformedChallengeRepositoryTest extends DbTestUtil{
	@Autowired
	private ChallengeRepository challengeRepositoryDao;
	
	@Autowired
	private PerformedChallengeRepository pfcDao;
	
	@Autowired
	private ChallengeEntityRepository entityDao;
	
	
	@Autowired
	private PlayerRepository playerDao;
	
	private Player bob;										//Bob id == 1L
	
	private PerformedChallenge pfc;
	
	private ChallengeEntity entity;
	
	
	/*
	 * Creation of a TaskGroupProgression
	 */
	@Before
	public void initializer() {
		bob = playerDao.findOne(1L);
		
		entity = new ChallengeEntity(bob.getId(), ChallengeEntity.PLAYERTYPE);
		
		pfc = new PerformedChallenge(Arrays.asList(entity),challengeRepositoryDao.findOne(8L));
		

		entity = entityDao.save(entity);
		//save the performed challenge
		pfc.setEntities(Arrays.asList(entity));
		pfc = pfcDao.save(pfc);
		entity.setPerformedChallenge(pfc);
		
	}
	
	@After
	public void restoreDB() {
		pfcDao.delete(pfc);
		entityDao.delete(entity);
	}
	
	
	@Test
	public void checkInstanceTest() {
		PerformedChallenge performed = pfcDao.findOne(pfc.getId());
		Assert.assertEquals(performed.getEntities().get(0).getType(), entity.getType());
	}
	
	@Test
	public void checkEntityTypeTest() {
		Assert.assertEquals(ChallengeEntity.PLAYERTYPE, entityDao.getEntityType(bob.getId()));;
	}
	
}
