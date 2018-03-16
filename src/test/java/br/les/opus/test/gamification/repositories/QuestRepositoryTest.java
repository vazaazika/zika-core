package br.les.opus.test.gamification.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import br.les.opus.gamification.domain.Quest;
import br.les.opus.gamification.repositories.QuestRepository;
import br.les.opus.test.util.DbTestUtil;

public class QuestRepositoryTest  extends DbTestUtil{
	@Autowired
	private QuestRepository questDao;
	
	@Test
	public void searchOnRepositoryTest() {
		Quest quest = questDao.findOne(1L);
		Assert.notNull(quest);
	}
	
	@Test
	public void searchOnRepositoryNullTest() {
		Quest quest = questDao.findOne(2L);
		Assert.isNull(quest);
	}

}
