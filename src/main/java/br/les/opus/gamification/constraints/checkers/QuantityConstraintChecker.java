package br.les.opus.gamification.constraints.checkers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.les.opus.gamification.constraints.AssignmentConstraint;
import br.les.opus.gamification.constraints.QuantityConstraint;
import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.repositories.PerformedTaskRepository;

@Component
public class QuantityConstraintChecker implements ConstraintChecker {
	
	@Autowired
	private PerformedTaskRepository performedTaskDao;

	@Override
	public Integer completedWork(AssignmentConstraint constraint, PerformedTask performedTask) {
		QuantityConstraint quantityConstraint = (QuantityConstraint)constraint;
		Task task = constraint.getTaskAssignment().getTask();
		Long count = performedTaskDao.countByPlayerAndTask(performedTask.getPlayer(), task);
		if (count >= quantityConstraint.getQuantity()) {
			return constraint.getWorkload();
		}
		return count.intValue();
	}

}
