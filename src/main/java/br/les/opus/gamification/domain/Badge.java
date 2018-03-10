package br.les.opus.gamification.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.les.opus.commons.persistence.IdAware;

@Entity
@Table(name = "game_badge")
public class Badge implements IdAware<Long> {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_GAME_BADGE")
	private Long id;
	
	private String name;
	
	private String description;
	
	private String imageUrl;
	
	@OneToOne(mappedBy = "badge")
	private TaskGroup taskGroup;
	
	@Transient
	private Float completion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public TaskGroup getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(TaskGroup group) {
		this.taskGroup = group;
	}

	public Float getCompletion() {
		return completion;
	}

	public void setCompletion(Float completion) {
		this.completion = completion;
	}

	@Override
	public String toString() {
		return "Badge [id=" + id + ", name=" + name + "]";
	}
}
