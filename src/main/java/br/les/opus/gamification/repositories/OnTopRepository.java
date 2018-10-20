package br.les.opus.gamification.repositories;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Membership;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Team;
import br.les.opus.gamification.domain.challenge.OnTop;
import br.les.opus.gamification.services.MembershipService;

@Repository
public class OnTopRepository extends HibernateAbstractRepository<OnTop, Long>{
	@Autowired
	private MembershipService membershipService;

	public OnTop findOneByTeam(Long teamId) {
		String hql = "from OnTop where team.id = :tId";
		Query query = getSession().createQuery(hql);
		query.setParameter("tId", teamId);
		
		return (OnTop) query.uniqueResult();
	}
	
	public OnTop getOnTop() {
		String hql = "from OnTop where ontop = true";
		Query query = getSession().createQuery(hql);
		
		return (OnTop) query.uniqueResult();
	}

	public OnTop getOnTopTeamMaxXP() {
		String hql = "from OnTop where team.id = (select t.id from Team as t where t.xp=(select max(g.xp) from Team as g))";
		Query query = getSession().createQuery(hql);
		
		return (OnTop) query.uniqueResult();
	}
	
	public boolean isPlayerTeamEnrolledInOnTopChallenge(Player player) {
		//get the player team
		Membership membership = membershipService.findCurrentMembership(player);
		
		if(membership == null) {
			return false;
		}
		
		Team team = membership.getTeam();
		
		//verify if team is on top challenge
		OnTop onTop = findOneByTeam(team.getId());
		
		if(onTop == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean isPlayerTeamWinnerOnTopChallenge(Player player) {
		//get the player team
		Membership membership = membershipService.findCurrentMembership(player);
		
		if(membership == null) {
			return false;
		}
		
		Team team = membership.getTeam();
		
		
		OnTop playerTeam = findOneByTeam(team.getId());
		OnTop onTopTeam = getOnTop();
		
		//verify if player team wont the challenge
		return playerTeam.equals(onTopTeam);
	}

}
