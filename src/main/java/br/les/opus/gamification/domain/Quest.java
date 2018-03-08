package br.les.opus.gamification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "game_quest")
@PrimaryKeyJoinColumn(name = "task_group_id")
public class Quest extends TaskGroup {
	/**
	 * Minimum level required for a player to register progress in the quest
	 */
	@Column(nullable=false)
	private Integer minLevel;
	
	@Column(nullable=false, unique=true)
	private String name;
	
	public boolean canProgress(Player player) {
		return minLevel != null && player.getLevel() >= minLevel;
	}
}
