package br.les.opus.gamification.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.les.opus.commons.persistence.IdAware;
import br.les.opus.gamification.validators.UniqueTeamName;

@Entity
@UniqueTeamName(payload = {}) //ensures validation on insert/update regarding team name
@Table(name = "game_team")
public class Team implements IdAware<Long> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_GAME_TEAM")
	private Long id;
	
	@NotNull
	@Length(max = 250)
	@Column(unique=true, nullable=false)
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "team")
	private List<Membership> memberships;
	
	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player creator;
	
	@JsonIgnore
	@Version
    @Column(name="opt_lock")
	private Integer version;
	
	@Column(nullable = false)
	private Long xp;
	
	@Transient
	@JsonIgnore
	public boolean wasCreatedBy(Player player) {
		return this.creator != null && this.creator.getId().equals(player.getId());
	}
	
	public Team() {
		this.version = 0;
		this.xp = 0L;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Membership> getMemberships() {
		return memberships;
	}

	public void setMemberships(List<Membership> memberships) {
		this.memberships = memberships;
	}

	public Player getCreator() {
		return creator;
	}

	public void setCreator(Player creator) {
		this.creator = creator;
	}
	
	public Integer getVersion() {
		return version;
	}

	public Long getXp() {
		return xp;
	}

	public void setXp(Long xp) {
		this.xp = xp;
	}
	public void addXp(Long xp) {
		this.xp += xp;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((memberships == null) ? 0 : memberships.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		result = prime * result + ((xp == null) ? 0 : xp.hashCode());
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
		Team other = (Team) obj;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (memberships == null) {
			if (other.memberships != null)
				return false;
		} else if (!memberships.equals(other.memberships))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		if (xp == null) {
			if (other.xp != null)
				return false;
		} else if (!xp.equals(other.xp))
			return false;
		return true;
	}

}
