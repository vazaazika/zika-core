package br.les.opus.gamification.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.les.opus.auth.core.domain.User;
import br.les.opus.gamification.LevelingSystem;

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
	
	@JsonIgnore
	@OneToMany(mappedBy = "player")
	private List<PerformedTask> performedTasks;
	
	@JsonIgnore
	@OneToMany(mappedBy = "player")
	private List<Membership> memberships;
	
	@JsonIgnore
	@OneToMany(mappedBy = "player")
	private List<TaskGroupProgression> progressions;
	
	public Player(User user) {
		this();
		this.setName(user.getName());
		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword());
		this.setNickname(user.getUsername());
	}
	
	public Player() {
		super();
		this.xp = 0l;
		this.level = 1;
	}
	
	@Transient
	public Integer getXpCurrentLevel() {
		return LevelingSystem.requiredXp(this.level);
	}
	
	@Transient
	public Integer getXpNextLevel() {
		return LevelingSystem.requiredXp(this.level + 1);
	}
	
	public void addXp(Integer xp) {
		this.xp += xp;
		this.level = LevelingSystem.computeLevel(this.xp.intValue());
	}

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

	public List<TaskGroupProgression> getProgressions() {
		return progressions;
	}

	public void setProgressions(List<TaskGroupProgression> progressions) {
		this.progressions = progressions;
	}

	@Override
	public String toString() {
		return "Player [nickname=" + nickname + "]";
	}
	
}
