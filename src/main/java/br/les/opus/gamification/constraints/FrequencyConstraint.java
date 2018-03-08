package br.les.opus.gamification.constraints;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.les.opus.gamification.domain.PerformedTask;

@Entity
@Table(name = "game_assignment_constraint_frequency")
@PrimaryKeyJoinColumn(name = "constraint_id")
public class FrequencyConstraint extends AssignmentConstraint {
	
	/**
	 * Time window to be considered (in seconds)
	 */
	private Integer timeWindow;
	
	/**
	 * Quantity of performed tasks in the time window the player
	 * must have done to satisfy the constraint
	 */
	private Integer quantity;

	@Override
	public boolean isSatisfied(PerformedTask performedTask) {
		//TODO implementar
		return false;
	}

}
