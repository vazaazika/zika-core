package br.les.opus.gamification.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Badge;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskGroup;
import br.les.opus.gamification.domain.TaskGroupProgression;
import br.les.opus.gamification.domain.challenge.Challenge;
import br.les.opus.gamification.domain.challenge.ChallengeName;

@Repository
public class BadgeRepository extends HibernateAbstractRepository<Badge, Long>{	
	@Autowired
	private StrikeChallengeRepository strikeDao;
	
	@Autowired
	private FightChallengeRepository fightDao;
	
	@Autowired
	private TeamUpChallengeRepository teamUpDao;
	
	
	@Autowired
	private OnTopRepository onTopDao;

	@SuppressWarnings("unchecked")
	public List<Badge> findAllWithProgressions(Player player) {
		String hql = "from TaskGroup tg left join tg.progressions ps with ps.player.id = :pId";
		Query query = getSession().createQuery(hql);
		query.setParameter("pId", player.getId());
		
		List<Object[]> badgesAndProgressions = query.list();
		List<Badge> badges = new ArrayList<>();
		
		for (Object[] objects : badgesAndProgressions) {
			if (objects[0] instanceof Challenge	) {
				Challenge challenge = (Challenge) objects[0];
				
				/*
				 * Strike Challenge
				 */
				if(challenge.getName().equals(ChallengeName.STRIKE.getName())){
					Badge badge = challenge.getBadge();
					
					/*
					 * verify if the player finished the strike challenge
					 */
					if(strikeDao.isPlayerWinner(player)) {
						badge.setCompletion(1F);
					}else {
						badge.setCompletion(-1F);
					}
					badges.add(badge);
					continue;
				}
				
				/*
				 * Fight Challenge
				 */
				if(challenge.getName().equals(ChallengeName.FIGHT.getName())){
					Badge badge = challenge.getBadge();
					
					/*
					 * verify if the player finished the strike challenge
					 */
					if(fightDao.isPlayerWinner(player)) {
						badge.setCompletion(1F);
					}else {
						badge.setCompletion(-1F);
					}
					badges.add(badge);
					continue;
				}
				
				/*
				 * OnTop Challenge
				 */
				if(challenge.getName().equals(ChallengeName.ONTOP.getName())){
					Badge badge = challenge.getBadge();
					
					//verify if player Team won the challenge On Top
					if(onTopDao.isPlayerTeamWinnerOnTopChallenge(player)) {
						badge.setCompletion(1F);
					}else {
						badge.setCompletion(-1F);
					}
					badges.add(badge);
					continue;
					
				}
				
				/*
				 * TeamUp Challenge
				 */
				if(challenge.getName().equals(ChallengeName.TEAMUP.getName())){
					Badge badge = challenge.getBadge();
					
					//verify if player Team won the challenge On Top
					if(teamUpDao.isPlayerWinner(player)) {
						badge.setCompletion(1F);
					}else {
						badge.setCompletion(-1F);
					}
					badges.add(badge);
					continue;
					
				}
				
				
				
				/*System.out.println(challenge.getName());
				
				if(challenge != null && challenge.getName().equals(ChallengeName.ONTOP.getName())) {
					Badge badge = challenge.getBadge();
					
					//verify if player Team won the challenge On Top
					if(onTopDao.isPlayerTeamWinnerOnTopChallenge(player)) {
						badge.setCompletion(1F);
					}else {
						badge.setCompletion(-1F);
					}
					badges.add(badge);
					continue;
				}
				
				Badge badge = challenge.getBadge();
				
				List<PerformedChallenge> pChallenge = pcDao.findAllPerformedChallengeByPlayerAndChallenge(player, challenge);
				
				//player is not in the challenge
				if(pChallenge == null) {
					badge.setCompletion(-1F);
				}else {
					//verify if the player concluded the challenge
					for(PerformedChallenge pc: pChallenge) {
						if(pc.isSucceed()) {
							badge.setCompletion(1F);
							break;
						}
					}
					
					if (badge.getCompletion() == null) {
						badge.setCompletion(-1F);
					}
				}
				badges.add(badge);
				
				continue;*/
			}

			
			TaskGroup taskGroup =  (TaskGroup)objects[0];
			Badge badge = taskGroup.getBadge();
			if (objects[1] != null) {
				TaskGroupProgression progression = (TaskGroupProgression)objects[1];
				badge.setCompletion(progression.getProgress());
			}
			
			if (badge.getCompletion() == null) {
				badge.setCompletion(-1F);
			}
			badges.add(badge);
		}
		
		Collections.sort(badges, new Comparator<Badge>() {

			@Override
			public int compare(Badge b1, Badge b2) {
				return (b2.getCompletion() > b1.getCompletion()) ? 1: -1;
			}
		});
		
		return badges;
	}
}
