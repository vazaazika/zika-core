package br.les.opus.gamification.repositories;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.challenge.FightChallenge;

@Repository
public class FightChallengeRepository extends HibernateAbstractRepository<FightChallenge, Long>{
	
	public FightChallenge findOneByPlayers(Player challenger, Player rival) {
		String hql = "from FightChallenge where (challenger.id = :cId and rival.id = :rId) or (challenger.id = :rId and rival.id = :cId)";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("cId", challenger.getId());
		query.setParameter("rId", rival.getId());
		
		return (FightChallenge) query.uniqueResult();
	}

}
