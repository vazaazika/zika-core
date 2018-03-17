package br.les.opus.test.gamification.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskGroup;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.test.util.DbTestUtil;

public class PlayerRepositoryTest  extends DbTestUtil{
	@Autowired
	private PlayerRepository playerDao;
	
	@Test
	public void sumCompletedWorkTest() {
		Player player = Mockito.mock(Player.class); 
		Mockito.when(player.getId()).thenReturn(1L);
		
		TaskGroup group = Mockito.mock(TaskGroup.class);
		Mockito.when(group.getId()).thenReturn(1L);
		
		Assert.assertEquals(new Long(1), playerDao.sumCompletedWork(group, player));
	}
	
	@Test
	public void sumCompleted2WorksTest() {
		Player player = Mockito.mock(Player.class); 
		Mockito.when(player.getId()).thenReturn(1L);
		
		TaskGroup group = Mockito.mock(TaskGroup.class);
		Mockito.when(group.getId()).thenReturn(1L);
		
		Assert.assertEquals(new Long(1), playerDao.sumCompletedWork(group, player));
	}

}
