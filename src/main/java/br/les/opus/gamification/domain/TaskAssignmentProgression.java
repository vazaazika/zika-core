package br.les.opus.gamification.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * This class maps a {@link Player} to a {@link TaskAssignment}. It is the way the system
 * track the player progress in a particular {@link TaskAssignment}
 *  
 * @author Diego Cedrim
 */
@Entity
@Table(
	name = "game_assignment_progression",
	uniqueConstraints=@UniqueConstraint(columnNames={"task_assignment_id", "player_id"})
)
public class TaskAssignmentProgression extends Progression {

	@ManyToOne
	@JoinColumn(name = "task_assignment_id", nullable=false)
	private TaskAssignment taskAssignment;

	public TaskAssignment getTaskAssignment() {
		return taskAssignment;
	}

	public void setTaskAssignment(TaskAssignment taskAssignment) {
		this.taskAssignment = taskAssignment;
	}
	
}
