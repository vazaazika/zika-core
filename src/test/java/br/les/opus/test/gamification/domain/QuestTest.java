package br.les.opus.test.gamification.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Quest;
import br.les.opus.gamification.repositories.QuestRepository;
import br.les.opus.test.util.DbTestUtil;

public class QuestTest extends DbTestUtil{
	@Autowired
	private QuestRepository questDao;
	
	private Quest quest;
	private Player player;
	
	@Before
	public void initializer() {
		quest = questDao.findOne(1L);
		player = Mockito.mock(Player.class);
	}
	
	@Test
	public void QuestCanProgressTrueLimitTest() {
		Mockito.when(player.getLevel()).thenReturn(10);
		
		Assert.assertTrue(quest.canProgress(player));
	}
	
	@Test
	public void QuestCanProgressTrueLimitSuperiorTest() {
		Mockito.when(player.getLevel()).thenReturn(11);
		
		Assert.assertTrue(quest.canProgress(player));
	}
	
	@Test
	public void QuestCanProgressFalseLimitInferiorTest() {
		Mockito.when(player.getLevel()).thenReturn(9);
		
		Assert.assertFalse(quest.canProgress(player));
	}

}
