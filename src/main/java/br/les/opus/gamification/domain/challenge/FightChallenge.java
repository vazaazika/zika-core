package br.les.opus.gamification.domain.challenge;

import java.util.Date;

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

import br.les.opus.commons.persistence.IdAware;
import br.les.opus.gamification.domain.Player;

/**
 * A mapping between players and FightChallenge
 * @author Leonardo Sousa
 */
@Entity
@Table(name = "game_fight_challenge")
public class FightChallenge implements IdAware<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_MEMBERSHIP")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "challenger_id")
	private Player challenger;
	
	@ManyToOne
	@JoinColumn(name = "rival_id")
	private Player rival;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	private boolean complete;
	
	private String status;
	
	public FightChallenge() {
		this.complete = false;
		this.status = InvitationStatus.ONHOLD.getValue();
		this.createdDate = new Date();
		this.startDate = null;
	}
	
	public FightChallenge(Player challenger, Player rival) {
		this();
		this.challenger = challenger;
		this.rival = rival;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Player getChallenger() {
		return challenger;
	}

	public void setChallenger(Player challenger) {
		this.challenger = challenger;
	}

	public Player getRival() {
		return rival;
	}

	public void setRival(Player rival) {
		this.rival = rival;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
