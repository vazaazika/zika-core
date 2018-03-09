package br.les.opus.gamification.constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.les.opus.gamification.constraints.checkers.QuantityConstraintChecker;
import br.les.opus.gamification.domain.TaskAssignment;

@Entity
@Table(name = "game_assignment_constraint_quantity")
@PrimaryKeyJoinColumn(name = "constraint_id")
public class QuantityConstraint extends AssignmentConstraint {
	
	/**
	 * It represents how many times the user must accomplish the linked task
	 * to the system consider this {@link TaskAssignment} done
	 */
	@Column(nullable=false)
	private Integer quantity;
	
	@Override
	public Integer getWorkload() {
		return quantity;
	}

	@Override
	public Class<?> getCheckerClass() {
		return QuantityConstraintChecker.class;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}