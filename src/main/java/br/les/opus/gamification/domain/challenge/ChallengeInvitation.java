package br.les.opus.gamification.domain.challenge;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.les.opus.gamification.domain.MailBody;
import br.les.opus.gamification.domain.Player;

public class ChallengeInvitation {
	private MailBody mail;
	private final String URL = "http://localhost:8080/zika-api/game/challenge/fight/accept/";

	public ChallengeInvitation() {
		mail = new MailBody();
	}
	
	public MailBody createFightChallengeInvitation(Player challenger, Player rival, Long idFightChallenge) {
		mail.setTo(rival.getUsername());
		mail.setSubject("Convite para paraticipar do desafio " + ChallengeName.FIGHT.getName());
		mail.setText("Seu amigo " + challenger.getName() + " está lhe convidando para participar do"
				+ " desafio " + ChallengeName.FIGHT.getName() + " \nPara aceitar o convite, acesse o link:"
						+ URL + idFightChallenge); 
		return mail;
	}

	
	public ResponseEntity<String> getAcceptanceMessage(Player challenger, Player rival, Challenge fightChallenge) {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.valueOf("text/html;charset=UTF-8"));
		
		headers.set("myheader", "Desafio Aceito");
		
		StringBuffer buffer = new StringBuffer();
		
		
		buffer.append("Olá " + rival.getName());
		buffer.append("<p>Agora voce esta participando do desafio: " + fightChallenge.getName() + "<br>");
		buffer.append(fightChallenge.getDescription());
		buffer.append("<p> <strong>O Desafio se inicia nesse momento. Boa sorte!<strong>");
		
		
		return new ResponseEntity<String>(buffer.toString(), headers, HttpStatus.OK);
	}
	
	public ResponseEntity<String> getExpirationMessage(Player challenger, Player rival, Challenge fightChallenge) {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.valueOf("text/html;charset=UTF-8"));
		
		headers.set("myheader", "Desafio Inválido");
		
		StringBuffer buffer = new StringBuffer();
		
		
		buffer.append("Olá " + rival.getName());
		buffer.append("<p>O desafio " + fightChallenge.getName() + " a qual você foi convidado expirou.<br>");
		
		
		return new ResponseEntity<String>(buffer.toString(), headers, HttpStatus.PRECONDITION_FAILED);
	}

	public ResponseEntity<String> getAlreadyEnrolledMessage(Player challenger, Player rival, Challenge fightChallenge) {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.valueOf("text/html;charset=UTF-8"));
		
		headers.set("myheader", "Desafio Em Progresso");
		
		StringBuffer buffer = new StringBuffer();
		
		
		buffer.append("Olá " + rival.getName());
		buffer.append("<p>Voce já esta participando do desafio: " + fightChallenge.getName() + "<br>");
		
		
		return new ResponseEntity<String>(buffer.toString(), headers, HttpStatus.ALREADY_REPORTED);
	}

}
