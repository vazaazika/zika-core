package br.les.opus.gamification.domain.challenge;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.les.opus.commons.persistence.IdAware;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskGroup;

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
	
	@ManyToOne
	@JoinColumn(name = "type_id", nullable=false, unique=false)
	private ChallengeType type;
	
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
	
	public String getType() {
		return type.getName();
	}
	public ChallengeType getChallengeType() {
		return type;
	}



	public void setChallengeType(ChallengeType type) {
		this.type = type;
	}
}
