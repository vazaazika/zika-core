package br.les.opus.gamification.domain.challenge;

public enum ChallengeName {
	STRIKE("Strike Mosquito"),
	ONTOP("On Top"),
	FIGHT("Fight!"),
	TEAMUP("Team Up!");
	
	private String name;
	
	private ChallengeName() {
		this.name= name();
	}
	
	private ChallengeName(String name) {
		this.name= name;
	}
	
	public String getName() {
		return name;
	}

}
