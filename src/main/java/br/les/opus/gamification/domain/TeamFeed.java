package br.les.opus.gamification.domain;

import java.util.ArrayList;
import java.util.List;

public class TeamFeed{
	private Team team;
	
	private List<Player> members;
	
	public TeamFeed(Team team) {
		this.team = team;
		this.members = new ArrayList<>();
	}
	
	public TeamFeed(Team team, Player player) {
		this.team = team;
		
		members = new ArrayList<>();
		members.add(player);
	}
	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public List<Player> getMembers() {
		return members;
	}
	
	public void addMember(Player player) {
		this.members.add(player);
	}

	public void addMembers(List<Player> players) {
		this.members = players;
	}
	
}
