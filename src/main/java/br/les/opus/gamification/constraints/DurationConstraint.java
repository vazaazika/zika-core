package br.les.opus.gamification.constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.les.opus.gamification.constraints.checkers.DurationConstraintChecker;
import br.les.opus.gamification.domain.TaskAssignment;

@Entity
@Table(name = "game_assignment_constraint_duration")
@PrimaryKeyJoinColumn(name = "constraint_id")
public class DurationConstraint extends AssignmentConstraint {
	
	/**
	 * It represents the duration of a challenge
	 * to the system consider this {@link TaskAssignment} done
	 */
	@Column(name= "duration_days", nullable=false)
	private Long durationDays;
	

	@Override
	public Class<?> getCheckerClass() {
		return DurationConstraintChecker.class;
	}
	
	/**
	 * This class has a workload equal to zero.
	 * This constraints is only to control how long a challenge is valid
	 */
	@Override
	public Integer getWorkload() {
		return 0;
	}

	public Long getDuration() {
		return durationDays;
	}

	public void setDuration(Long duration) {
		this.durationDays = duration;
	}

}