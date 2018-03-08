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

/**
 * A mapping between player and team
 * @author Diego Cedrim
 */
@Entity
@Table(name = "game_membership")
public class Membership {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_MEMBERSHIP")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "player_id", nullable=false)
	private Player player;
	
	@ManyToOne
	@JoinColumn(name = "player_id", nullable=false)
	private Team team;
	
	/**
	 * Indicates whether the membership is active or not. A player can have
	 * only one active membership
	 */
	@Column(nullable=false)
	private Boolean active;
	
	public Membership() {
		this.active = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
