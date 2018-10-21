package br.les.opus.gamification.domain.challenge;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.les.opus.gamification.domain.MailBody;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Team;

public class ChallengeInvitation {
	private MailBody mail;
	private final String URL = "http://localhost:8080/zika-api/game/challenge/";

	public ChallengeInvitation() {
		mail = new MailBody();
	}
	
	public MailBody createFightChallengeInvitation(Player challenger, Player rival, Long idFightChallenge) {
		mail.setTo(rival.getUsername());
		mail.setSubject("Convite para paraticipar do desafio " + ChallengeName.FIGHT.getName());
		mail.setText("Seu amigo " + challenger.getName() + " está lhe convidando para participar do"
				+ " desafio " + ChallengeName.FIGHT.getName() + " \nPara aceitar o convite, acesse o link:"
						+ URL + "fight/accept/" + idFightChallenge); 
		return mail;
	}

	
	public ResponseEntity<String> getAcceptanceMessage(Player rival, Challenge challenge) {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.valueOf("text/html;charset=UTF-8"));
		
		headers.set("myheader", "Desafio Aceito");
		
		StringBuffer buffer = new StringBuffer();
		
		
		buffer.append("Olá " + rival.getName());
		buffer.append("<p>Agora voce esta participando do desafio: " + challenge.getName() + "<br>");
		buffer.append(challenge.getDescription());
		buffer.append("<p> <strong>O Desafio se inicia nesse momento. Boa sorte!<strong>");
		
		
		return new ResponseEntity<String>(buffer.toString(), headers, HttpStatus.OK);
	}
	
	public ResponseEntity<String> getExpirationMessage(Player rival, Challenge challenge) {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.valueOf("text/html;charset=UTF-8"));
		
		headers.set("myheader", "Desafio Inválido");
		
		StringBuffer buffer = new StringBuffer();
		
		
		buffer.append("Olá " + rival.getName());
		buffer.append("<p>O desafio " + challenge.getName() + " a qual você foi convidado expirou.<br>");
		
		
		return new ResponseEntity<String>(buffer.toString(), headers, HttpStatus.PRECONDITION_FAILED);
	}
	
	public ResponseEntity<String> getTeamExpirationMessage(Challenge challenge) {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.valueOf("text/html;charset=UTF-8"));
		
		headers.set("myheader", "Desafio Inválido");
		
		StringBuffer buffer = new StringBuffer();
		
		
		buffer.append("Olá ");
		buffer.append("<p>O desafio " + challenge.getName() + " a qual você foi convidado expirou.<br>");
		
		
		return new ResponseEntity<String>(buffer.toString(), headers, HttpStatus.PRECONDITION_FAILED);
	}
	
	public ResponseEntity<String> getTeamAcceptanceMessage(Challenge challenge) {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.valueOf("text/html;charset=UTF-8"));
		
		headers.set("myheader", "Desafio Aceito");
		
		StringBuffer buffer = new StringBuffer();
		
		
		buffer.append("Olá ");
		buffer.append("<p>Agora voce esta participando do desafio: " + challenge.getName() + "<br>");
		buffer.append(challenge.getDescription());
		buffer.append("<p> <strong>O Desafio se inicia nesse momento. Boa sorte!<strong>");
		
		
		return new ResponseEntity<String>(buffer.toString(), headers, HttpStatus.OK);
	}

	public ResponseEntity<String> getAlreadyEnrolledMessage(Player rival, Challenge challenge) {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.valueOf("text/html;charset=UTF-8"));
		
		headers.set("myheader", "Desafio Em Progresso");
		
		StringBuffer buffer = new StringBuffer();
		
		
		buffer.append("Olá " + rival.getName());
		buffer.append("<p>Voce já esta participando do desafio: " + challenge.getName() + "<br>");
		
		
		return new ResponseEntity<String>(buffer.toString(), headers, HttpStatus.ALREADY_REPORTED);
	}
	
	public ResponseEntity<String> getAlreadyEnrolledTeamMessage(Challenge challenge) {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.valueOf("text/html;charset=UTF-8"));
		
		headers.set("myheader", "Desafio Em Progresso");
		
		StringBuffer buffer = new StringBuffer();
		
		
		buffer.append("Olá ");
		buffer.append("<p>O seu time já esta participando do desafio: " + challenge.getName() + "<br>");
		
		
		return new ResponseEntity<String>(buffer.toString(), headers, HttpStatus.ALREADY_REPORTED);
	}

	public MailBody createTeamUpChallengeInvitation(Player challenger, Team challengerTeam, Player rival, Long idTeamUpChallenge) {
		mail.setTo(rival.getUsername());
		mail.setSubject("Convite para paraticipar do desafio " + ChallengeName.TEAMUP.getName());
		mail.setText("Seu amigo " + challenger.getName() + " do time " + challengerTeam.getName() + " está lhe convidando para participar do"
			+ " desafio " + ChallengeName.TEAMUP.getName() + " \nPara aceitar o convite, acesse o link:"
					+ URL +  "teamup/accept/" + idTeamUpChallenge); 
		return mail;
	}

}
