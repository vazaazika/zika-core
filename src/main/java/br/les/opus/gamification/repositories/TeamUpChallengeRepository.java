package br.les.opus.gamification.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Team;
import br.les.opus.gamification.domain.challenge.InvitationStatus;
import br.les.opus.gamification.domain.challenge.TeamUpChallenge;

@Repository
public class TeamUpChallengeRepository extends HibernateAbstractRepository<TeamUpChallenge, Long>{
	
	public TeamUpChallenge findOneByPlayers(Team challenger, Team rival) {
		String hql = "from TeamUpChallenge where (challenger.id = :cId and rival.id = :rId) or (challenger.id = :rId and rival.id = :cId)";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("cId", challenger.getId());
		query.setParameter("rId", rival.getId());
		
		return (TeamUpChallenge) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<TeamUpChallenge> findOpenChallenges() {
		String hql = "from TeamUpChallenge where complete = :cValue and status = :status";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("cValue", false);
		query.setParameter("status", InvitationStatus.ACCEPTED.getValue());
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public Page<TeamUpChallenge> findAllByTeams(Team challenger, Team rival, Pageable pageable) {
		String hql = "from TeamUpChallenge where (challenger.id = :cId and rival.id = :rId) or (challenger.id = :rId and rival.id = :cId)";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("cId", challenger.getId());
		query.setParameter("rId", rival.getId());
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		
		return new PageImpl<>(query.list(), pageable, this.countTeamUpChallengeByTeams(challenger, rival));
		
	}
	
	public Long countTeamUpChallengeByTeams(Team challenger, Team rival) {
		String hql = "select count(f.id) from TeamUpChallenge f where (f.challenger.id = :cId and f.rival.id = :rId) or (f.challenger.id = :rId and f.rival.id = :cId)";
		Query query = getSession().createQuery(hql);
		query.setParameter("cId", challenger.getId());
		query.setParameter("rId", rival.getId());
		return (Long)query.uniqueResult();
	}

}
