package br.les.opus.gamification.services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import br.les.opus.gamification.constraints.DurationConstraint;
import br.les.opus.gamification.constraints.QuantityConstraint;
import br.les.opus.gamification.constraints.checkers.DurationConstraintChecker;
import br.les.opus.gamification.domain.Membership;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.domain.Team;
import br.les.opus.gamification.domain.challenge.Challenge;
import br.les.opus.gamification.domain.challenge.ChallengeEntity;
import br.les.opus.gamification.domain.challenge.ChallengeName;
import br.les.opus.gamification.domain.challenge.OnTop;
import br.les.opus.gamification.domain.challenge.PerformedChallenge;
import br.les.opus.gamification.repositories.ChallengeRepository;
import br.les.opus.gamification.repositories.OnTopRepository;
import br.les.opus.gamification.repositories.PerformedChallengeRepository;
import br.les.opus.gamification.repositories.PerformedTaskRepository;
import br.les.opus.gamification.repositories.PlayerRepository;

@Service
@Transactional
public class ChallengeService {
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private ChallengeRepository repository;
	
	@Autowired
	private PerformedChallengeRepository pcDao;
	
	@Autowired
	private PerformedTaskRepository ptDao;
	
	@Autowired
	private OnTopRepository onTopDao;
	
	@Autowired
	private PlayerRepository playerDao;
	
	/**
	 * verify if the challenge has any constraint
	 * @param challengeId
	 * @return
	 */
	
	public boolean checkChallenges(Long challengeId){
		return true;
	}
	
	public void verifyOpenChallenges() {
		/*
		 * Get regular challenges
		 */
		List<PerformedChallenge> openChallenges = getIncompletePerformedChallenges();
		
		for(PerformedChallenge pc: openChallenges) {
			shouldBeClosed(pc);
		}
		
		/*
		 * Get OnTopChallenges
		 */
		//List<OnTop> openOnTops = onTopDao.findAll();
		handleOnTopChallenge();
		
	}

	public List<PerformedChallenge> getIncompletePerformedChallenges() {
		List<PerformedChallenge> openChallenges = pcDao.findIncompletePerformedChallenge();
		
		return openChallenges;
	}
	
	private void shouldBeClosed(PerformedChallenge pc) {
		Challenge challenge = pc.getChallenge();
		
		if(challenge.getName().equals(ChallengeName.STRIKE.getName())) {
			handleStrikeChallenge(pc, challenge);
		}
		
		if(challenge.getName().equals(ChallengeName.FIGHT.getName())) {
			handleFightChallenge(pc, challenge);
		}
		
	}
	
	private void handleStrikeChallenge(PerformedChallenge pc, Challenge challenge) {
		
		DurationConstraint durationConstraint = repository.getDurationConstraint(challenge.getId());
		
		if(durationConstraint == null) {
			return;
		}
		
		DurationConstraintChecker checker = (DurationConstraintChecker)ctx.getBean(durationConstraint.getCheckerClass());
		
		//verify if the challenge should be closed due to time
		if(checker.completedWork(durationConstraint, pc) == 0) {//
			return;
		}
		
		//the challenge expired, checking progress before closing it
		QuantityConstraint quantityConstraint = repository.getQuantityConstraint(challenge.getId());
		
		
		//get Player, task and date that the challenge started
		ChallengeEntity entity = pc.getEntities().get(0);
		Player player = playerDao.findOne(entity.getIdEntity());
		Task task = challenge.getAssignments().get(0).getTask();
		Date dateStart = pc.getDate();
		
		//get the pois that the player did during the time that the challenge was active
		Long nPois = ptDao.countByPlayerAndTaskPeriodDate(player, task, dateStart);
		
		if(nPois >= quantityConstraint.getId()) {//player receives the xp
			player.addXp(challenge.getGivenXp());
			pc.setSucceed(true);
		}else {
			pc.setSucceed(false);
		}
		
		pc.setComplete(true);
	}
	
	private void handleFightChallenge(PerformedChallenge pc, Challenge challenge) {
		
		DurationConstraint durationConstraint = repository.getDurationConstraint(challenge.getId());
		
		if(durationConstraint == null) {
			return;
		}
		
		DurationConstraintChecker checker = (DurationConstraintChecker)ctx.getBean(durationConstraint.getCheckerClass());
		
		//verify if the challenge should be closed due to time
		if(checker.completedWork(durationConstraint, pc) == 0) {//
			return;
		}
		
		
		//get Players, task and date that the challenge started
		List<ChallengeEntity> entities = pc.getEntities();
		Player player1 = playerDao.findOne(entities.get(0).getIdEntity());
		Player player2 = playerDao.findOne(entities.get(1).getIdEntity());
		
		
		//if one of the players is null, then the other one receives the XP
		if(player1 == null || player2 == null) {
			if(player1 == null) {
				player2.addXp(challenge.getGivenXp());
			}else {
				player1.addXp(challenge.getGivenXp());
			}
			
			pc.setSucceed(true);
			pc.setComplete(true);
			
			return;
		}
		
		Task task = challenge.getAssignments().get(0).getTask();
		Date dateStart = pc.getDate();
		
		
		/*
		 * identify the player who scored the most
		 */
		Long p1Pois = ptDao.countByPlayerAndTaskPeriodDate(player1, task, dateStart);
		Long p2Pois = ptDao.countByPlayerAndTaskPeriodDate(player2, task, dateStart);
		
		
		if(p1Pois > p2Pois) {
			player1.addXp(challenge.getGivenXp());
			player2.addXp(challenge.getGivenXp()/5);
		}else {
			if(p1Pois < p2Pois) {
				player2.addXp(challenge.getGivenXp());
				player1.addXp(challenge.getGivenXp()/5);
			}else {
				player1.addXp(challenge.getGivenXp());
				player2.addXp(challenge.getGivenXp());
			}
		}
		
		pc.setSucceed(true);
		pc.setComplete(true);
	}
	
	private void handleOnTopChallenge() {
		Challenge challenge = repository.findChallengeByName(ChallengeName.ONTOP.getName());
		OnTop onTop = onTopDao.getOnTop();
		
		OnTop maxXp = onTopDao.getOnTopTeamMaxXP();
		
		if(!onTop.equals(maxXp)) {//if the team with max xp is different from the current on top
			onTop.setOnTop(false);
			maxXp.setOnTop(true);
			
			//update Xp for the team and its members
			Team team = maxXp.getTeam();
			List<Membership> memberships = team.getMemberships();
			team.addXp(Long.valueOf(challenge.getGivenXp()));
			
			for(Membership m: memberships) {
				if(m.getActive()) {
					m.getPlayer().addXp(300);
				}
			}
		}
		
	}
	
	public void sendInvitationFightChallente(Player challenger, Player rival) {
		
	}

}
