package br.les.opus.gamification.constraints.checkers;

import br.les.opus.gamification.constraints.AssignmentConstraint;
import br.les.opus.gamification.domain.PerformedTask;

public interface ConstraintChecker {

	Integer completedWork(AssignmentConstraint constraint, PerformedTask performedTask);
}
