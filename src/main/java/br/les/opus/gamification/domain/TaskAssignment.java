package br.les.opus.gamification.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.les.opus.gamification.constraints.AssignmentConstraint;

/**
 * It represents a mapping between a {@link Task} and a {@link TaskGroup}. The players must
 * complete all {@link TaskAssignment} to the system consider that they finished a {@link TaskGroup}.
 * 
 * @author Diego Cedrim
 */
@Entity
@Table(name = "game_task_assignment")
public class TaskAssignment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_GAME_TASK_ASSIGNMENT")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "task_id", nullable=false)
	private Task task;
	
	@ManyToOne
	@JoinColumn(name = "task_group_id", nullable=false)
	private TaskGroup taskGroup;
	
	@OneToMany(mappedBy="taskAssignment")
	private List<AssignmentConstraint> constraints;
	
	@OneToMany(mappedBy = "taskAssignment")
	private List<TaskAssignmentProgression> progressions;
	
	public TaskAssignment() {
		constraints = new ArrayList<>();
	}
	
	/**
	 * The sum of the workload of all constraints. If there is no constraint,
	 * the workload is equals 1
	 * @return the workload completed when the player finishes the task assignment
	 */
	public Integer getWorkload() {
		if (constraints.isEmpty()) {
			return 1;
		}
		Integer totalWorkload = 0;
		for (AssignmentConstraint assignmentConstraint : constraints) {
			totalWorkload += assignmentConstraint.getWorkload();
		}
		return totalWorkload;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public List<AssignmentConstraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<AssignmentConstraint> constraints) {
		this.constraints = constraints;
	}

	public TaskGroup getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(TaskGroup taskGroup) {
		this.taskGroup = taskGroup;
	}

	public List<TaskAssignmentProgression> getProgressions() {
		return progressions;
	}

	public void setProgressions(List<TaskAssignmentProgression> progressions) {
		this.progressions = progressions;
	}
	
}
