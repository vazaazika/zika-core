package br.les.opus.gamification.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.les.opus.auth.core.domain.User;

@Entity
@Table(name = "game_player")
@PrimaryKeyJoinColumn(name = "user_id")
public class Player extends User {

	private static final long serialVersionUID = 7818489613961400074L;

	@NotNull
	@Length(max = 100)
	@Column(nullable = false, unique=true)
	private String nickname;
	
	@Column(nullable = false)
	private Long xp;
	
	@Column(nullable = false)
	private Integer level;
	
	@OneToMany(mappedBy = "player")
	private List<PerformedTask> performedTasks;
	
	@OneToMany(mappedBy = "player")
	private List<Membership> memberships;

	public List<Membership> getMemberships() {
		return memberships;
	}

	public void setMemberships(List<Membership> memberships) {
		this.memberships = memberships;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Long getXp() {
		return xp;
	}

	public void setXp(Long xp) {
		this.xp = xp;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<PerformedTask> getPerformedTasks() {
		return performedTasks;
	}

	public void setPerformedTasks(List<PerformedTask> performedTasks) {
		this.performedTasks = performedTasks;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
