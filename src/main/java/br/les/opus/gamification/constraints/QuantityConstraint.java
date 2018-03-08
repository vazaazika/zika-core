package br.les.opus.gamification.constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.TaskAssignment;
import br.les.opus.gamification.repositories.PlayerRepository;

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

	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Override
	public boolean isSatisfied(PerformedTask performedTask) {
		Long count = playerRepository.countPerformedTasks(performedTask.getPlayer(), taskAssignment.getTask());
		return count >= quantity;
	}

}