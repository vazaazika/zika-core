package br.les.opus.test.gamification.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import br.les.opus.gamification.domain.challenge.Challenge;
import br.les.opus.gamification.repositories.ChallengeRepository;
import br.les.opus.test.util.DbTestUtil;

public class ChallengeRepositoryTest  extends DbTestUtil{
	@Autowired
	private ChallengeRepository challengeDao;
	
	@Test
	public void searchOnRepositoryTest() {
		Challenge challenge = challengeDao.findOne(1L);
		Assert.notNull(challenge);
	}

	@Test
	public void searchOnRepositoryNullTest() {
		Challenge challenge = challengeDao.findOne(2L);
		Assert.isNull(challenge);
	}

}
