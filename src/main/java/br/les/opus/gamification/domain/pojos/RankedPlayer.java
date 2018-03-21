package br.les.opus.gamification.domain.pojos;

import br.les.opus.gamification.domain.Player;

public class RankedPlayer {
	private Player player;
	
	private Long count;
	
	public RankedPlayer(Player player, Long count) {
		this.player = player;
		this.count = count;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
