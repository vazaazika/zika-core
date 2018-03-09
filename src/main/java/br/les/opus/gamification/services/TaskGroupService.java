package br.les.opus.gamification.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskAssignment;
import br.les.opus.gamification.domain.TaskAssignmentProgression;
import br.les.opus.gamification.domain.TaskGroup;
import br.les.opus.gamification.domain.TaskGroupProgression;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.TaskAssignmentProgressionRepository;
import br.les.opus.gamification.repositories.TaskAssignmentRepository;
import br.les.opus.gamification.repositories.TaskGroupProgressionRepository;

@Service
public class TaskGroupService {

	@Autowired
	private TaskAssignmentRepository taskAssignmentDao;
	
	@Autowired
	private TaskAssignmentProgressionRepository taProgressionDao;
	
	@Autowired
	private TaskGroupProgressionRepository tgProgressionDao;
	
	@Autowired
	private TaskAssignmentService taService;
	
	@Autowired
	private PlayerRepository playerDao;
	
	private TaskAssignmentProgression getAssignmentProgression(TaskAssignment assignment, Player player) {
		TaskAssignmentProgression progression = taProgressionDao.findByPlayer(assignment, player);
		if (progression == null) {
			progression = new TaskAssignmentProgression();
			progression.setPlayer(player);
			progression.setTaskAssignment(assignment);
			progression = taProgressionDao.save(progression);
		}
		return progression;
	}
	
	private TaskGroupProgression getGroupProgression(TaskGroup group, Player player) {
		TaskGroupProgression progression = tgProgressionDao.findByPlayer(group, player);
		if (progression == null) {
			progression = new TaskGroupProgression();
			progression.setPlayer(player);
			progression.setTaskGroup(group);
			progression = tgProgressionDao.save(progression);
		}
		return progression;
	}
	
	private void trackGroupsProgress(Set<TaskGroup> groups, Player player) {
		for (TaskGroup group : groups) {
			TaskGroupProgression progression = getGroupProgression(group, player);
			boolean isCompleteBefore = progression.isComplete();
			Long completedWork = playerDao.sumCompletedWork(group, player);
			Integer workload = group.getWorkload();
			progression.computeProgress(completedWork.intValue(), workload);
			tgProgressionDao.save(progression);
			if (!isCompleteBefore && progression.isComplete()) {
				// the player just completed the task group
				player.addXp(group.getGivenXp());
			}
		}
	}
	
	public void trackProgress(PerformedTask task) {
		Set<TaskGroup> affectedGroups = new HashSet<>();
		Player player = task.getPlayer();
		List<TaskAssignment> incompleteAssignments = taskAssignmentDao.findAllIncomplete(task);
		for (TaskAssignment assignment : incompleteAssignments) {
			if (!assignment.getTaskGroup().canProgress(player)) {
				continue;
			}
			
			/*
			 * Computes and updates the progress in the assignment
			 */
			TaskAssignmentProgression progression = this.getAssignmentProgression(assignment, player);
			int completedWorkBefore = progression.getCompletedWork();
			int completedWork = taService.completedWork(task, assignment);
			if (completedWorkBefore != completedWork) {
				int workload = assignment.getWorkload();
				progression.computeProgress(completedWork, workload);
				taProgressionDao.save(progression);
				affectedGroups.add(assignment.getTaskGroup());
			}
		}
		trackGroupsProgress(affectedGroups, player);
	}
}
