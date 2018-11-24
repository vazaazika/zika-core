package br.les.opus.gamification.domain.challenge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.les.opus.commons.persistence.IdAware;

/**
 * This class represents a registration of game challenge performed by a given user or team. It is a kind of
 * activities log that can be used for monitoring.
 * 
 * @author Leonardo
 */
@Entity
@Table(name = "game_performed_challenge")
public class PerformedChallenge implements IdAware<Long> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_GAME_PERFORMED_TASK")
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "PerformedChallenge")
	private List<ChallengeEntity> entities;
	
	@ManyToOne
	@JoinColumn(name = "challenge_id")
	private Challenge challenge;
	
	private boolean complete;
	
	private boolean succeed;
	
	public PerformedChallenge() {
		this.date = new Date();
		complete = false;
		entities = new ArrayList<>();
	}
	
	public PerformedChallenge(List<ChallengeEntity> entities, Challenge challenge) {
		this();
		this.entities = entities;
		this.challenge = challenge;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<ChallengeEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<ChallengeEntity> entities) {
		this.entities = entities;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	public void addEntity(ChallengeEntity entity) {
		this.entities.add(entity);
	}

	public boolean isSucceed() {
		return succeed;
	}

	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}
	
	
	
}
