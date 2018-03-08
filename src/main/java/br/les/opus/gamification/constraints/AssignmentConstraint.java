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

import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.TaskAssignment;

@Entity
@Table(name = "game_assignment_constraint")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AssignmentConstraint {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_ASSIGNMENT_CONSTRAINT")
	protected Long id;
	
	@ManyToOne
	@JoinColumn(name="task_assignment_id")
	protected TaskAssignment taskAssignment;
	
	protected String description;
	
	public abstract boolean isSatisfied(PerformedTask performedTask);

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
