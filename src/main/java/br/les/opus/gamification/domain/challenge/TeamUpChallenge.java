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
import br.les.opus.gamification.domain.Team;

/**
 * A mapping between players and FightChallenge
 * @author Leonardo Sousa
 */
@Entity
@Table(name = "game_teamup_challenge")
public class TeamUpChallenge implements IdAware<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_MEMBERSHIP")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "challenger_id")
	private Team challenger;
	
	@ManyToOne
	@JoinColumn(name = "rival_id")
	private Team rival;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	private boolean complete;
	
	private String status;
	
	/**
	 * retorna o id do vencesdor, -1 empate
	 */
	private Long winner;
	
	public TeamUpChallenge() {
		this.complete = false;
		this.status = InvitationStatus.ONHOLD.getValue();
		this.createdDate = new Date();
		this.startDate = null;
		this.winner = -1L;
	}
	
	public TeamUpChallenge(Team challenger, Team rival) {
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

	public Team getChallenger() {
		return challenger;
	}

	public void setChallenger(Team challenger) {
		this.challenger = challenger;
	}

	public Team getRival() {
		return rival;
	}

	public void setRival(Team rival) {
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

	public Long getWinner() {
		return winner;
	}

	public void setWinner(Long winner) {
		this.winner = winner;
	}
}
