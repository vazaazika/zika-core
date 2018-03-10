package br.les.opus.gamification.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class Progression {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_PROGRESSION")
	protected Long id;
	
	/**
	 * A value in the range [0,1] representing the player progress
	 */
	@Column(nullable=false)
	protected Float progress;
	
	@Column(nullable=false)
	protected Integer completedWork;
	
	/**
	 * This flag is set to true when progress = 1.
	 */
	@Column(nullable=false)
	protected Boolean complete;
	
	@ManyToOne
	@JoinColumn(name = "player_id", nullable=false)
	protected Player player;
	
	public Progression() {
		this.progress = 0f;
		this.complete = false;
		this.completedWork = 0;
	}
	
	public void computeProgress(Integer completedWork, Integer workload) {
		this.complete = completedWork >= workload;
		if (this.complete) {
			this.progress = 1f;
			this.completedWork = workload;
		} else {
			this.progress = completedWork.floatValue()/workload;
			this.completedWork = completedWork;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getProgress() {
		return progress;
	}

	public void setProgress(Float progress) {
		this.progress = progress;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Boolean getComplete() {
		return complete;
	}
	
	@Transient
	public boolean isComplete() {
		return complete;
	}

	public void setComplete(Boolean complete) {
		this.complete = complete;
	}

	public Integer getCompletedWork() {
		return completedWork;
	}

	public void setCompletedWork(Integer completedWork) {
		this.completedWork = completedWork;
	}
	
}
