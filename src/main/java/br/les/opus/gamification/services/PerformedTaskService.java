package br.les.opus.gamification.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.repositories.PerformedTaskRepository;

@Service
public class PerformedTaskService {
	
	@Autowired
	private PerformedTaskRepository performedTaskDao;

	public PerformedTask register(Task task, Player player) {
		PerformedTask performedTask = new PerformedTask(task, player);
		performedTask = performedTaskDao.save(performedTask);
		player.addXp(performedTask.getTask().getGivenXp());
		return performedTask;
	}
}
