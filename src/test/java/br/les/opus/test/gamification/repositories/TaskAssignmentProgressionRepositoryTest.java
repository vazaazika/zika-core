package br.les.opus.test.gamification.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskAssignment;
import br.les.opus.gamification.domain.TaskAssignmentProgression;
import br.les.opus.gamification.repositories.TaskAssignmentProgressionRepository;
import br.les.opus.test.util.DbTestUtil;

public class TaskAssignmentProgressionRepositoryTest extends DbTestUtil{
	@Autowired
	private TaskAssignmentProgressionRepository tapDao;
	
	private TaskAssignmentProgression taskProgression;
	
	@Test
	public void findByPlayerTest() {
		Player player = Mockito.mock(Player.class); 
		Mockito.when(player.getId()).thenReturn(1L);			//bob
		
		TaskAssignment assignment = Mockito.mock(TaskAssignment.class);
		Mockito.when(assignment.getId()).thenReturn(1L);		//assignment 1
		
		taskProgression = tapDao.findOne(1L);				//assignment 1
		
		//They should be the same
		Assert.assertEquals(taskProgression, tapDao.findByPlayer(assignment, player));
	}

}
