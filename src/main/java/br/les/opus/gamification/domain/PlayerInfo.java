package br.les.opus.gamification.domain;

/**
 * I created this class to avoid Infinite Recursion. For instance, assume that the player is the one
 * who created a team, in this case, we have player->team->creator->player, creating a loop.
 * 
 * To avoid that, I created this class (it's not the best way to solve the problem, but at least this
 * solution does not compromise the other services)
 * @author leonardo
 *
 */
public class PlayerInfo{
	private Player player;
	private Team team;
	
	public PlayerInfo(Player player) {
		this.player = player;
	}
	
	
	public Player getPlayer() {
		return player;
	}



	public void setPlayer(Player player) {
		this.player = player;
	}



	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	
}
