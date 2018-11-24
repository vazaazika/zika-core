package br.les.opus.gamification.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.challenge.Challenge;
import br.les.opus.gamification.domain.challenge.PerformedChallenge;

@Repository
public class PerformedChallengeRepository extends HibernateAbstractRepository<PerformedChallenge, Long>{
	
	@SuppressWarnings("unchecked")
	public List<PerformedChallenge> findIncompletePerformedChallenge() {
		String hql = "from PerformedChallenge where complete='f'";
		Query query = getSession().createQuery(hql);
		
		List<PerformedChallenge> list = query.list();
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<PerformedChallenge> findAllPerformedChallengeByPlayerAndChallenge(Player player, Challenge challenge) {
		String hql = "select distinct p from PerformedChallenge p, ChallengeEntity e where p.challenge.id = :challengeId and e.idEntity = :pId";
		Query query = getSession().createQuery(hql);
		query.setParameter("challengeId", challenge.getId());
		query.setParameter("pId", player.getId());
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PerformedChallenge> findAllIncompletePerformedChallengeByPlayerAndChallenge(Player player, Challenge challenge) {
		String hql = "select distinct p from PerformedChallenge p, ChallengeEntity e where p.complete='f' and p.challenge.id = :challengeId and e.idEntity = :pId";
		Query query = getSession().createQuery(hql);
		query.setParameter("challengeId", challenge.getId());
		query.setParameter("pId", player.getId());
		
		return query.list();
	}

}
