package br.les.opus.gamification.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.challenge.FightChallenge;
import br.les.opus.gamification.domain.challenge.InvitationStatus;

@Repository
public class FightChallengeRepository extends HibernateAbstractRepository<FightChallenge, Long>{
	
	public FightChallenge findOneByPlayers(Player challenger, Player rival) {
		String hql = "from FightChallenge where (challenger.id = :cId and rival.id = :rId) or (challenger.id = :rId and rival.id = :cId)";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("cId", challenger.getId());
		query.setParameter("rId", rival.getId());
		
		return (FightChallenge) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<FightChallenge> findOpenChallenges() {
		String hql = "from FightChallenge where complete = :cValue and status = :status";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("cValue", false);
		query.setParameter("status", InvitationStatus.ACCEPTED.getValue());
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public Page<FightChallenge> findAllByPlayers(Player challenger, Player rival, Pageable pageable) {
		String hql = "from FightChallenge where (challenger.id = :cId and rival.id = :rId) or (challenger.id = :rId and rival.id = :cId)";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("cId", challenger.getId());
		query.setParameter("rId", rival.getId());
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		
		return new PageImpl<>(query.list(), pageable, this.countFighChallengeByPlayers(challenger, rival));
		
	}
	
	public Long countFighChallengeByPlayers(Player challenger, Player rival) {
		String hql = "select count(f.id) from FightChallenge f where (f.challenger.id = :cId and f.rival.id = :rId) or (f.challenger.id = :rId and f.rival.id = :cId)";
		Query query = getSession().createQuery(hql);
		query.setParameter("cId", challenger.getId());
		query.setParameter("rId", rival.getId());
		return (Long)query.uniqueResult();
	}

}
