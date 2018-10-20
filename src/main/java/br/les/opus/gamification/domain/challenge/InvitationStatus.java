package br.les.opus.gamification.domain.challenge;

public enum InvitationStatus {
	ACCEPTED("Aceito"),
	REFUSED("Recusado"),
	ONHOLD("Em Espera");
	
	private String value;
	
	private InvitationStatus() {
		this.value = name();
	}
	
	private InvitationStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
