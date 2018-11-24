package br.les.opus.auth.core.domain;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Team;

public class TokenPlayer extends Token {
	private Player player;
	private Team team;
	
	public TokenPlayer() {
		super();
	}
	

	public TokenPlayer(UsernamePasswordAuthenticationToken authToken) {
		super(authToken);
	}

	public TokenPlayer(User user) {
		super(user);
		// TODO Auto-generated constructor stub
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}


	public void setTeam(Team team) {
		this.team = team;
	}
	
	public Team getTeam() {
		return team;
	}

}
