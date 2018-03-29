package br.les.opus.gamification.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	@JoinColumn(name = "team_id", nullable=false)
	private Team team;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	/**
	 * Indicates whether the membership is active or not. A player can have
	 * only one active membership
	 */
	@Column(nullable=false)
	private Boolean active;
	
	public Membership() {
		this.active = true;
		this.createdDate = new Date();
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
