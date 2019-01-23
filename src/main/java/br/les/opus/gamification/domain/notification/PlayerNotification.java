package br.les.opus.gamification.domain.notification;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.les.opus.commons.persistence.IdAware;
import br.les.opus.gamification.domain.Badge;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.notification.Notification.Type;

@Entity
@Table(name = "game_player_notification")
public class PlayerNotification implements IdAware<Long> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_GAME_TEAM")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player player;
	
	@JsonIgnore
	@Enumerated(EnumType.ORDINAL)
	private Notification.Type type;
	
	@Transient
	private Notification notification;
	
	@Transient
	private ArrayList<Badge> badges;
	
	
	private boolean consumed;
	
	
	
	public PlayerNotification(Player player, Type type, Notification notification) {
		this.player = player;
		this.type = type;
		this.notification = notification;
		consumed = false;
	}

	public PlayerNotification() {
		consumed = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Notification.Type getType() {
		return type;
	}

	public void setType(Notification.Type type) {
		this.type = type;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public boolean isConsumed() {
		return consumed;
	}

	public void setConsumed(boolean consumed) {
		this.consumed = consumed;
	}

	public ArrayList<Badge> getBadges() {
		return badges;
	}

	public void setBadges(ArrayList<Badge> badges) {
		this.badges = badges;
	}
	
}
