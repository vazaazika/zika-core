package br.les.opus.gamification.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class maps a {@link Player} to a {@link TaskGroup}. It is the way the system
 * track the player progress in a particular {@link TaskGroup}
 *  
 * @author Diego Cedrim
 */
@Entity
@Table(
	name = "game_task_group_progression",
	uniqueConstraints=@UniqueConstraint(columnNames={"task_group_id", "player_id"})
)
public class TaskGroupProgression extends Progression {
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "task_group_id", nullable=false)
	private TaskGroup taskGroup;
	
	public TaskGroupProgression() {
		this.progress = 0f;
		this.complete = false;
	}

	public TaskGroup getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(TaskGroup taskGroup) {
		this.taskGroup = taskGroup;
	}
	
}
