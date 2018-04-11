package br.les.opus.dengue.core.domain;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.les.opus.commons.persistence.IdAware;
import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;

@Entity
@Table(name = "game_performed_task_comment")
public class PerformedTaskComment implements IdAware<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_PERFORMED_TASK_COMMENT")
	private Long id;
	
	private Date date;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "performed_task_id")
	private PerformedTask performedTask;
	
	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player player;
	
	@NotNull
	@Length(max = 2000, min = 2)
	@Column(nullable = false, length = 2000)
	private String text;
	
	public PerformedTaskComment() {
		this.date = new Date();
	}

	@Transient
	@JsonIgnore
	public boolean isOwnedBy(Player player) {
		return this.player.equals(player);
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

	public PerformedTask getPerformedTask() {
		return performedTask;
	}

	public void setPerformedTask(PerformedTask performedTask) {
		this.performedTask = performedTask;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
