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
 * A mapping between a player and StrikeChallenge
 * @author Leonardo Sousa
 */
@Entity
@Table(name = "game_strike_challenge")
public class StrikeChallenge implements IdAware<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_MEMBERSHIP")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player player;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	private boolean complete;
	
	private boolean succeed;
	
	public StrikeChallenge() {
		this.complete = false;
		this.succeed = false;
		this.startDate = new Date();
	}
	
	public StrikeChallenge(Player player) {
		this();
		this.player = player;
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

	public boolean isSucceed() {
		return succeed;
	}

	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}
	
}
