package br.les.opus.test.gamification.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.repositories.BadgeRepository;
import br.les.opus.test.util.DbTestUtil;

public class BadgeRepositoryTest extends DbTestUtil{
	@Autowired
	private BadgeRepository badgeDao;
	
	@Test
	public void findAllWithProgressionsTest() {
		Player player = Mockito.mock(Player.class); 
		Mockito.when(player.getId()).thenReturn(1L);
		
		Assert.assertEquals(3, badgeDao.findAllWithProgressions(player).size());
	}

}
