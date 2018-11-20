package br.les.opus.gamification.domain.challenge;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.les.opus.commons.persistence.IdAware;

@Entity
@Table(name = "game_challenge_entity")
public class ChallengeEntity implements IdAware<Long> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_GAME_TEAM")
	private Long id;
	
	@Column(name="id_entity")
	private Long idEntity;		//Id for a player or for a team
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "performed_id")
	private PerformedChallenge PerformedChallenge;
	
	@NotNull
	@Length(max = 250)
	private String type;
	
	@Transient
	public static String PLAYERTYPE = "Player";
	
	@Transient
	public static String TEAMTYPE = "Team"; 
	
	public ChallengeEntity() {
		
	}
	
	public ChallengeEntity(Long idEntity, String type) {
		this.idEntity = idEntity;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getIdEntity() {
		return idEntity;
	}

	public void setIdEntity(Long idEntity) {
		this.idEntity = idEntity;
	}

	public PerformedChallenge getPerformedChallenge() {
		return PerformedChallenge;
	}

	public void setPerformedChallenge(PerformedChallenge performedChallenge) {
		PerformedChallenge = performedChallenge;
	}

}
