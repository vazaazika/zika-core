package br.les.opus.gamification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "game_task_group")
@Inheritance(strategy = InheritanceType.JOINED)
public class TaskGroup {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_GAME_TASK_GROUP")
	private Long id;
	
	/**
	 * Extra experience points given to a player that accomplishes the task group.
	 * The player can be rewarded by each task accomplished as well.
	 */
	@Column(nullable = false)
	private Integer givenXp;
	
	/**
	 * Badge given to a user that finishes all tasks in a {@link TaskGroup}
	 */
	@ManyToOne
	@JoinColumn(name = "badge_id", nullable=false)
	private Badge badge;

}
