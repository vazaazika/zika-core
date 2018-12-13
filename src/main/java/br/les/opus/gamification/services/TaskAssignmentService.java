package br.les.opus.gamification.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import br.les.opus.gamification.constraints.AssignmentConstraint;
import br.les.opus.gamification.constraints.checkers.ConstraintChecker;
import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.TaskAssignment;

@Service
public class TaskAssignmentService {
	
	@Autowired
	private ApplicationContext ctx;
	
	public Integer completedWork(PerformedTask performedTask, TaskAssignment assignment) {
		int completed = 0;
		/*
		 * If there is no constraint, then we can consider the player finished the
		 * task assignment, so we just return the assignment's workload as completed work
		 */
		if (assignment.getConstraints().isEmpty()) {
			return assignment.getWorkload();
		}
		/*
		 * Otherwise, we must compute, for each constraint, the completed work
		 */
		for (AssignmentConstraint constraint : assignment.getConstraints()) {
			ConstraintChecker checker = (ConstraintChecker)ctx.getBean(constraint.getCheckerClass());
			int work = checker.completedWork(constraint, performedTask);
			completed += work;
		}
		return completed;
	}
}
