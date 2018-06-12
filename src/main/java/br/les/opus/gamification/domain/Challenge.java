package br.les.opus.gamification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.les.opus.commons.persistence.IdAware;

/**
 * Class to store the challenges performed by a group or a single player
 * @author leonardo
 *
 */
@Entity
@Table(name = "game_challenge")
@PrimaryKeyJoinColumn(name = "task_group_id")
public class Challenge extends TaskGroup implements IdAware<Long>{
	
	@Column(nullable=false, unique=true)
	private String name;
	
	@Column(nullable=false, unique=false)
	private String description;
	
	
	private String type;
	
	/**
	 * Checks if a player can register progress in the challenge.
	 * @param player player trying to register progress
	 * @return true if the player can have progress, false otherwise
	 */
	public boolean canProgress(Player player) {
		return true;//to do
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



	@Transient
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
