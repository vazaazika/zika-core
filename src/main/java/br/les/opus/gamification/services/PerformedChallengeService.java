package br.les.opus.gamification.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.challenge.Challenge;
import br.les.opus.gamification.domain.challenge.ChallengeEntity;
import br.les.opus.gamification.domain.challenge.PerformedChallenge;
import br.les.opus.gamification.repositories.ChallengeEntityRepository;
import br.les.opus.gamification.repositories.PerformedChallengeRepository;
import br.les.opus.gamification.repositories.PlayerRepository;

@Service
public class PerformedChallengeService {
	@Autowired
	private PerformedChallengeRepository pfcDao;
	
	@Autowired
	private ChallengeEntityRepository entityDao;
	
	
	@Autowired
	private PlayerRepository playerDao;
	
	public  PerformedChallenge startSingleChallenge(Challenge challenge, Player player) {
		//create the entity to log
		System.out.println("entrou meu senhor");
		return null;
	}
	
}
