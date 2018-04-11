package br.les.opus.gamification.constraints;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.les.opus.gamification.domain.TaskAssignment;

@Entity
@Table(name = "game_assignment_constraint")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AssignmentConstraint {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_ASSIGNMENT_CONSTRAINT")
	protected Long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="task_assignment_id")
	protected TaskAssignment taskAssignment;
	
	protected String description;
	
	@JsonIgnore
	@Transient
	public abstract Class<?> getCheckerClass();
	
	/**
	 * By default, when a player satisfies a constraint, he is exactly one step closer
	 * to finish the related task assignment. However, special constraints can have
	 * a heavier workload. It means the player must perform more tasks to satisfy it.
	 * For instance, the workload for a {@link QuantityConstraint} is equals to the
	 * specified quantity. Computing the total and the completed workload can be used
	 * to measure the player progress in the TaskAssignment.
	 * @return the workload completed when the player satisfies the constraint
	 */
	@Transient
	public Integer getWorkload() {
		return 1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskAssignment getTaskAssignment() {
		return taskAssignment;
	}

	public void setTaskAssignment(TaskAssignment taskAssignment) {
		this.taskAssignment = taskAssignment;
	}
	
}
