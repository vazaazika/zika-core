package br.les.opus.test.gamification.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.auth.core.domain.Resource;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.repositories.TaskRepository;
import br.les.opus.test.util.DbTestUtil;

public class TaskRepositoryTest  extends DbTestUtil{
	@Autowired
	private TaskRepository taskDao;
	
	@Test
	public void searchOnRepositoryTest() {
		Task task = taskDao.findOne(1L);
		Assert.assertEquals(new Long(1L), task.getId());
	}
	
	@Test
	public void findByResourceTest() {
		Task task = taskDao.findOne(1L);
		
		Resource resource = Mockito.mock(Resource.class);
		Mockito.when(resource.getId()).thenReturn(8L);	//resource id in the db
		
		
		Assert.assertEquals(task, taskDao.findByResource(resource));
	}
	
	@Test
	public void findByResourceDifferentTest() {
		Task task = taskDao.findOne(1L);
		
		Resource resource = Mockito.mock(Resource.class);
		Mockito.when(resource.getId()).thenReturn(6L);	//resource id in the db
		
		
		Assert.assertNotEquals(task, taskDao.findByResource(resource));
	}

}
