package br.les.opus.gamification.domain.notification;

public class Notification {
	private String title;
	
	private String description;

	public Notification() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public enum Type{
		LEVELUP("Mudança de nível"),
		WINNER("Jogador ganhou o desafio"),
		LOOSER("Jogador perdeu o desafio"),
		ONTOP("Time no topo"),
		WINNERTEAM("Time ganhou o desafio"),
		LOOSERTEAM("Time perdeu o desafio"),
		BADGE("Jogador ganhou badge");
		
		private String label;
		
		private Type(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
		
		@Override
		public String toString() {
			return label;
		}
	}

}
