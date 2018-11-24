package br.les.opus.test.gamification.services;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.domain.TaskAssignmentProgression;
import br.les.opus.gamification.domain.TaskGroupProgression;
import br.les.opus.gamification.domain.pojos.DailyRecord;
import br.les.opus.gamification.domain.pojos.PlayerRecords;
import br.les.opus.gamification.repositories.PerformedTaskRepository;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.TaskAssignmentProgressionRepository;
import br.les.opus.gamification.repositories.TaskGroupProgressionRepository;
import br.les.opus.gamification.repositories.TaskRepository;
import br.les.opus.gamification.services.TaskGroupService;
import br.les.opus.test.util.DbTestUtil;
import java.util.Date;
import org.joda.time.LocalDateTime;

public class TaskGroupServiceTest extends DbTestUtil{
	@Autowired
	private TaskGroupService tgService;
	
	@Autowired
	private TaskAssignmentProgressionRepository tapDao;
	
	@Autowired
	private TaskGroupProgressionRepository tgpDao;
	
	@Autowired
	private PerformedTaskRepository performedTaskRepositoryDao;

	@Autowired
	private PlayerRepository playerDao;
	
	@Autowired
	private TaskRepository taskRepositoryDao;
	
	private Player bob;								//Bob id == 1L
	
	private Task task;								//Task id = 2L
	
	//private TaskAssignment taskAssignment;
	
	private PerformedTask performedTask;
	
	
	/*
	 * Creation of a PerformedTask
	 */
	@Before
	public void initializer() {
		bob = playerDao.findOne(1l);
		task = taskRepositoryDao.findOne(2l);
		
		
		//taskAssignment = taDao.findOne(1L);

		performedTask = new PerformedTask();
		performedTask.setPlayer(bob);
		performedTask.setTask(task);

		performedTask = performedTaskRepositoryDao.save(performedTask);
	}
	
	@After
	public void restoreDB() {
		performedTaskRepositoryDao.delete(performedTask);
	}
	
	/*
	 * After calling trackProgress, two new progression are created.
	 * 
	 * The first one created is a TaskAssignmentProgression.
	 * Since our database already had three TaskAssignmentProgressions, the fourth one is created
	 * 
	 * The second progression created is a TaskGroupProgression.
	 * Since our database had none TaskGroupProgression, the first one is stored in it.
	 * 
	 */
	@Test
	public void trackProgressTest() {
		System.out.println(">@@@ " + bob.getXp()); 
		tgService.trackProgress(performedTask);
		System.out.println(">@@@ " + bob.getXp());
		Assert.assertEquals(2, tapDao.count());
		Assert.assertEquals(2, tgpDao.count());
		
		//Retrieving all stored progressions
		List<TaskAssignmentProgression> tapList = tapDao.findAll();
		List<TaskGroupProgression> tgpList = tgpDao.findAll();
		
		/*
		 * Removing the last stored progressions to keep the database consistent
		 * and doesn't mess up with other test cases
		 */
		for (TaskAssignmentProgression taskAssignmentProgression : tapList) {
			System.out.println(">@ " + taskAssignmentProgression.getProgress() + "/   " + taskAssignmentProgression.getTaskAssignment().getTask().getGivenXp() + " / " + taskAssignmentProgression.getTaskAssignment().getTask().getDescription());
		}
		System.out.println(tapList);
		System.out.println(tgpList);
		tapDao.delete(tapList.get(tapList.size() - 1));
		tgpDao.delete(tgpList.get(tgpList.size() - 1));
		System.out.println(">@@@ " + bob.getXp());
		
		DailyRecord dailyRecord = performedTaskRepositoryDao.dailyRecord(bob);
		
		Assert.assertEquals(performedTask.getTask().getGivenXp(), dailyRecord.getXp());
		
		Date begin = LocalDateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate();
		Date end = LocalDateTime.now().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
		
		PlayerRecords records = new PlayerRecords();
		records.setTodayXp(performedTaskRepositoryDao.sumXpByPlayerAndInterval(bob, begin, end));
		records.setBestDayRecord(performedTaskRepositoryDao.dailyRecord(bob));
		records.setTotalXp(bob.getXp());
		
		Assert.assertEquals(records.getTodayXp(), bob.getXp());
		Assert.assertEquals(records.getTotalXp(), bob.getXp());
		
		System.out.println(records.getBestDayRecord());
	}
	
}
