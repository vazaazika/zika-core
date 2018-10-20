package br.les.opus.gamification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.les.opus.auth.core.domain.Resource;

/**
 * This class represents a mapping between a system resource and a game task. It is a way
 * of registering in the system all requests that will generate experience points to the player.
 * 
 * @author Diego Cedrim
 */
@Entity
@Table(name = "game_task")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_GAME_TASK")
	private Long id;
	
	/**
	 * Experience points given to a user if this task is accomplished
	 */
	@Column(nullable=false)
	private Integer givenXp;
	
	/**
	 * Resource that represents this task as a HTTP request
	 */
	@ManyToOne
	@JoinColumn(name = "resource_id", nullable=false, unique=true)
	private Resource resource;
	
	private String description;

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

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource usedResource) {
		this.resource = usedResource;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
