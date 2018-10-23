package br.les.opus.gamification.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import br.les.opus.gamification.constraints.DurationConstraint;
import br.les.opus.gamification.constraints.QuantityConstraint;
import br.les.opus.gamification.constraints.checkers.DurationConstraintChecker;
import br.les.opus.gamification.domain.MailBody;
import br.les.opus.gamification.domain.Membership;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.domain.Team;
import br.les.opus.gamification.domain.challenge.Challenge;
import br.les.opus.gamification.domain.challenge.ChallengeEntity;
import br.les.opus.gamification.domain.challenge.ChallengeInvitation;
import br.les.opus.gamification.domain.challenge.ChallengeName;
import br.les.opus.gamification.domain.challenge.FightChallenge;
import br.les.opus.gamification.domain.challenge.OnTop;
import br.les.opus.gamification.domain.challenge.PerformedChallenge;
import br.les.opus.gamification.domain.challenge.TeamUpChallenge;
import br.les.opus.gamification.repositories.ChallengeRepository;
import br.les.opus.gamification.repositories.FightChallengeRepository;
import br.les.opus.gamification.repositories.OnTopRepository;
import br.les.opus.gamification.repositories.PerformedChallengeRepository;
import br.les.opus.gamification.repositories.PerformedTaskRepository;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.TeamUpChallengeRepository;

@Service
@Transactional
public class ChallengeService {
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private ChallengeRepository repository;
	
	@Autowired
	private FightChallengeRepository fightDao;
	
	@Autowired
	private PerformedChallengeRepository pcDao;
	
	@Autowired
	private PerformedTaskRepository ptDao;
	
	@Autowired
	private OnTopRepository onTopDao;
	
	@Autowired
	private TeamUpChallengeRepository teamUpDao;
	
	@Autowired
	private PlayerRepository playerDao;
	
	@Autowired
    private MailService mailService;

	
	public void verifyOpenChallenges() {
		/*
		 * Get regular challenges
		 *
		List<PerformedChallenge> openChallenges = getIncompletePerformedChallenges();
		
		for(PerformedChallenge pc: openChallenges) {
			shouldBeClosed(pc);
		}*/
		
		/*
		 * FighChallenge
		 */
		//handleFightChallenges();
		
		
		/*
		 * OnTopChallenges
		 */
		//handleOnTopChallenges();
		
		/*
		 * TeamUpChallenges
		 */
		//handleTeamUpChallenges();
		
	}
	
	
	/**
	 * verify if the challenge has any constraint
	 * @param challengeId
	 * @return
	 */
	
	public boolean checkChallenges(Long challengeId){
		return true;
	}
	
	/* ******************************************************************************************
	 * 
	 * 										Strike Challenge
	 * 
	 * *****************************************************************************************/

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
			//handleFightChallenge(pc, challenge);
		}
		
	}
	
	private void handleStrikeChallenge(PerformedChallenge pc, Challenge challenge) {
		
		DurationConstraint durationConstraint = repository.getDurationConstraint(challenge.getId());
		
		if(durationConstraint == null) {
			return;
		}
		
		DurationConstraintChecker checker = (DurationConstraintChecker)ctx.getBean(durationConstraint.getCheckerClass());
		
		//verify if the challenge should be closed due to time
		if(checker.completedWork(durationConstraint, pc.getDate()) == 0) {//
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
	
	/*private void handleFightChallenge(PerformedChallenge pc, Challenge challenge) {
		
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
		 *
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
	}*/

	/* ******************************************************************************************
	 * 
	 * 										Fight Challenge
	 * 
	 * *****************************************************************************************/
	private void handleFightChallenges() {
		List<FightChallenge> challenges = fightDao.findOpenChallenges();
		
		for(FightChallenge fc: challenges) {
			handleFightChallenge(fc);
		}
	}
	
	private void handleFightChallenge(FightChallenge fc) {
		Challenge challenge = repository.findChallengeByName(ChallengeName.FIGHT.getName());
		DurationConstraint durationConstraint = repository.getDurationConstraint(challenge.getId());
		
		if(durationConstraint == null) {
			return;
		}
		
		DurationConstraintChecker checker = (DurationConstraintChecker)ctx.getBean(durationConstraint.getCheckerClass());
		
		//verify if the challenge should be closed due to time
		if(checker.completedWork(durationConstraint, fc.getStartDate()) == 0) {//
			return;
		}
		
		
		//get Players, task and date that the challenge started
		Player player1 = fc.getChallenger();
		Player player2 = fc.getRival();
		
		
		//if one of the players is null, then the other one receives the XP
		if(player1 == null || player2 == null) {
			if(player1 == null && player2 != null) {
				player2.addXp(challenge.getGivenXp());
				fc.setWinner(player2.getId());
			}else {
				if(player1 != null && player2 == null) {
					player1.addXp(challenge.getGivenXp());
					fc.setWinner(player1.getId());
				}
			}
			
			fc.setComplete(true);
			
			return;
		}
		
		Task task = challenge.getAssignments().get(0).getTask();
		Date dateStart = fc.getStartDate();
		
		
		/*
		 * identify the player who scored the most
		 */
		Long p1Pois = ptDao.countByPlayerAndTaskPeriodDate(player1, task, dateStart);
		Long p2Pois = ptDao.countByPlayerAndTaskPeriodDate(player2, task, dateStart);
		
		
		if(p1Pois > p2Pois) {
			player1.addXp(challenge.getGivenXp());
			player2.addXp(challenge.getGivenXp()/5);
			fc.setWinner(player1.getId());
		}else {
			if(p1Pois < p2Pois) {
				player2.addXp(challenge.getGivenXp());
				player1.addXp(challenge.getGivenXp()/5);
				fc.setWinner(player2.getId());
			}else {				
				player1.addXp(challenge.getGivenXp());
				player2.addXp(challenge.getGivenXp());
			}
		}
		
		fc.setComplete(true);
	}
	
	/* ******************************************************************************************
	 * 
	 * 										OnTop Challenge
	 * 
	 * *****************************************************************************************/
	
	private void handleOnTopChallenges() {
		Challenge challenge = repository.findChallengeByName(ChallengeName.ONTOP.getName());
		OnTop onTop = onTopDao.getOnTop();
		
		OnTop maxXp = onTopDao.getOnTopTeamMaxXP();
		
		if(!onTop.equals(maxXp)) {//if the team with max xp is different from the current on top
			onTop.setOnTop(false);
			maxXp.setOnTop(true);
			
			//update Xp for the team and its members
			Team team = maxXp.getTeam();
			giveTemMembersXp(team, challenge.getGivenXp(), 100);
		}
		
	}
	
	/* ******************************************************************************************
	 * 
	 * 										TeamUp Challenge
	 * 
	 * *****************************************************************************************/
	private void handleTeamUpChallenges() {
		List<TeamUpChallenge> challenges = teamUpDao.findOpenChallenges();
		
		for(TeamUpChallenge teamUpChallenge: challenges) {
			handleTeamUpChallenge(teamUpChallenge);
		}
		
	}
	
	private void handleTeamUpChallenge(TeamUpChallenge teamUpChallenge) {
		Challenge challenge = repository.findChallengeByName(ChallengeName.TEAMUP.getName());
		DurationConstraint durationConstraint = repository.getDurationConstraint(challenge.getId());
		
		if(durationConstraint == null) {
			return;
		}
		
		DurationConstraintChecker checker = (DurationConstraintChecker)ctx.getBean(durationConstraint.getCheckerClass());
		
		//verify if the challenge should be closed due to time
		if(checker.completedWork(durationConstraint, teamUpChallenge.getStartDate()) == 0) {//
			return;
		}
		
		//get teams
		Team challenger = teamUpChallenge.getChallenger();
		Team rival = teamUpChallenge.getRival();
		
		//if one of the players is null, then the other one receives the XP
		if(challenger == null || rival == null) {
			if(challenger == null && rival != null) {
				giveTemMembersXp(rival, challenge.getGivenXp(), 100);
				teamUpChallenge.setWinner(rival.getId());
			}else {
				if(challenger != null && rival == null) {
					giveTemMembersXp(challenger, challenge.getGivenXp(), 100);
					teamUpChallenge.setWinner(challenger.getId());
				}
			}
			
			teamUpChallenge.setComplete(true);
			
			return;
		}
		
		Task task = challenge.getAssignments().get(0).getTask();
		Date dateStart = teamUpChallenge.getStartDate();
		
		
		/*
		 * identify the Team that members scored the most
		 */
		Long challengerMembers = getMembersXp(challenger.getMemberships(), task, dateStart);
		Long rivalMembers = getMembersXp(rival.getMemberships(), task, dateStart);
		
		if(challengerMembers > rivalMembers) {
			giveTemMembersXp(challenger, challenge.getGivenXp(), 100);
			giveTemMembersXp(rival, challenge.getGivenXp()/5, 50);
			teamUpChallenge.setWinner(challenger.getId());
		}else {
			if(challengerMembers < rivalMembers) {
				giveTemMembersXp(rival, challenge.getGivenXp(), 100);
				giveTemMembersXp(challenger, challenge.getGivenXp()/5, 50);
				teamUpChallenge.setWinner(rival.getId());
			}else {				
				giveTemMembersXp(challenger, challenge.getGivenXp(), 100);
				giveTemMembersXp(rival, challenge.getGivenXp(), 100);
			}
		}
		
		teamUpChallenge.setComplete(true);
		
	}


	private void giveTemMembersXp(Team team, Integer teamGivenXp, Integer memberGivenXp) {
		//update Xp for the team and its members
		List<Membership> memberships = team.getMemberships();
		
		team.addXp(Long.valueOf(teamGivenXp));
		
		for(Membership m: memberships) {
			if(m.getActive()) {
				m.getPlayer().addXp(memberGivenXp);
			}
		}
	}
	
	private Long getMembersXp(List<Membership> members, Task task, Date date) {
		Long xp = 0L;
		
		if(members == null || members.isEmpty()) {
			return xp;
		}
		
		for(Membership m: members) {
			if(m.getActive()) {
				xp += ptDao.countByPlayerAndTaskPeriodDate(m.getPlayer(), task, date);
			}
		}
		
		return xp;
	}


	public void sendInvitationFightChallente(Player challenger, Player rival, Long idFightChallenge) {
		ChallengeInvitation invitation = new ChallengeInvitation();
		
		MailBody mail = invitation.createFightChallengeInvitation(challenger, rival, idFightChallenge);
		
		mailService.setMail(mail);
		mailService.start();
	}

	
	/**
	 * 
	 * Check if date is valid (less then 24h)
	 * 
	 *
	 * @param date = Date
	 * @return true if the date is valid, false if the date is invalid
	 */
	public boolean checkValidDate(Date date) {
		
		Calendar calendar  = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		
		if(calendar.getTime().compareTo(new Date()) < 0) {
			return false;
		}else {
			return true;
		}
		
	}

	public void sendInvitationTeamUpChallente(Player loggedPlayer, Team challengerTeam, Team rivalTeam, Long idTeamUpChallenge) {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		
		ChallengeInvitation invitation;
		
		List<Membership> members = rivalTeam.getMemberships();
		
		
		for(Membership m: members) {
			Player rival = m.getPlayer();
			
			invitation = new ChallengeInvitation();
			MailBody mail = invitation.createTeamUpChallengeInvitation(loggedPlayer, challengerTeam, rival, idTeamUpChallenge);
			
			mailService.setMail(mail);
			
			executor.execute(mailService);
		}
			
		executor.shutdown();
		
        while (!executor.isTerminated()) {
        }
		
	}
	

}
