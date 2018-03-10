package br.les.opus.gamification.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "game_task_group")
@Inheritance(strategy = InheritanceType.JOINED)
public class TaskGroup {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_GAME_TASK_GROUP")
	private Long id;
	
	/**
	 * Extra experience points given to a player that accomplishes the task group.
	 * The player can be rewarded by each task accomplished as well.
	 */
	@Column(nullable = false)
	private Integer givenXp;
	
	/**
	 * Badge given to a user that finishes all tasks in a {@link TaskGroup}
	 */
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "badge_id", nullable=false)
	private Badge badge;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "taskGroup")
	private List<TaskAssignment> assignments;
	
	@JsonIgnore
	@OneToMany(mappedBy = "taskGroup")
	private List<TaskGroupProgression> progressions;
	
	public TaskGroup() {
		this.assignments = new ArrayList<>();
	}
	
	@Transient
	public String getType() {
		return "simple";
	}
	
	/**
	 * The sum of the workload of all assignments. 
	 * @return the workload completed when the player finishes the task group
	 */
	public Integer getWorkload() {
		Integer totalWorkload = 0;
		for (TaskAssignment assignment : this.assignments) {
			totalWorkload += assignment.getWorkload();
		}
		return totalWorkload;
	}
	
	public boolean canProgress(Player player) {
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getGivenXp() {
		return givenXp;
	}

	public void setGivenXp(Integer givenXp) {
		this.givenXp = givenXp;
	}

	public Badge getBadge() {
		return badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskGroup other = (TaskGroup) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<TaskAssignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<TaskAssignment> assignments) {
		this.assignments = assignments;
	}

	public List<TaskGroupProgression> getProgressions() {
		return progressions;
	}

	public void setProgressions(List<TaskGroupProgression> progressions) {
		this.progressions = progressions;
	}
	
}
