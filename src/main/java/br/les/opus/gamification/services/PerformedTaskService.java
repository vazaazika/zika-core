package br.les.opus.gamification.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.repositories.PerformedTaskRepository;

@Service
public class PerformedTaskService {
	
	@Autowired
	private PerformedTaskRepository performedTaskDao;

	public void register(PerformedTask performedTask) {
		performedTask = performedTaskDao.save(performedTask);
		Player player = performedTask.getPlayer();
		//TODO isso também precisa ser thread safe! refatorar
		player.addXp(performedTask.getTask().getGivenXp());
	}
}
