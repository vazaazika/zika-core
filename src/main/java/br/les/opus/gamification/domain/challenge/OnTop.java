package br.les.opus.gamification.domain.challenge;

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

import br.les.opus.gamification.domain.Team;

/**
 * A mapping between player and team
 * @author Leonardo Sousa
 */
@Entity
@Table(name = "game_ontop_challenge")
public class OnTop {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_MEMBERSHIP")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "team_id", nullable=false)
	private Team team;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	/**
	 * Indicates whether the team is the winner
	 */
	@Column(nullable=false)
	private Boolean onTop;
	
	public OnTop() {
		this.onTop = false;
		this.createdDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Boolean getOnTop() {
		return onTop;
	}

	public void setOnTop(Boolean onTop) {
		this.onTop = onTop;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((onTop == null) ? 0 : onTop.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OnTop other = (OnTop) obj;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (onTop == null) {
			if (other.onTop != null)
				return false;
		} else if (!onTop.equals(other.onTop))
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (team.getId() != other.team.getId())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OnTop [id=" + id + ", team=" + team.getName() + ", createdDate=" + createdDate + ", onTop=" + onTop + "]";
	}
	
	
	
}
