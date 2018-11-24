package br.les.opus.gamification.xpgivers;

import br.les.opus.gamification.domain.Player;

public class GivenXP {
	private Player owner;
	
	private Integer xpAmount;
	
	public GivenXP(Player owner, Integer givenXp) {
		this.owner = owner;
		this.xpAmount = givenXp;
	}
	
	public GivenXP() {

	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Integer getXpAmount() {
		return xpAmount;
	}

	public void setXpAmount(Integer givenXp) {
		this.xpAmount = givenXp;
	}
	
}
