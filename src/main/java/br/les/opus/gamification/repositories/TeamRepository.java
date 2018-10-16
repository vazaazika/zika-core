package br.les.opus.gamification.repositories;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Team;
import br.les.opus.gamification.domain.TeamFeed;

@Repository
public class TeamRepository extends HibernateAbstractRepository<Team, Long>{

	public Long countActiveMembers(Long teamId) {
		String hql = "select count(m.player) from Membership m where m.team.id = :id and m.active = true";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", teamId);
		return (Long)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public Page<Player> findAllActiveMembers(Pageable pageable, Long teamId) {
		String hql = "select m.player from Membership m where m.team.id = :id and m.active = true order by m.player.level desc";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", teamId);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return new PageImpl<>(query.list(), pageable, this.countActiveMembers(teamId));
	}
	
	public Team findByName(String name) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq("name", name).ignoreCase());
		Object obj = criteria.uniqueResult();
		return (obj == null)? null : (Team)obj;
	}
	
	public Page<TeamFeed> findAllTeamsWithActiveMembers(Pageable pageable){
		String hql  = "select new br.les.opus.gamification.domain.TeamFeed(t.id, t.name, br.les.opus.gamification.domain.Player(p.level, p.nickname, p.xp)) "
				+ "from Team as t, Player as p, Membership as m "
				+ "where m.team.id=t.id and p.id=m.player.id";
		Query query = getSession().createQuery(hql);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<TeamFeed> teams = query.list();
		
		return new PageImpl<TeamFeed>(teams,pageable, teams.size());
	}
	
	public Team getTeamMaxXP() {
		String hql = "from Team as t where t.xp=(select max(g.xp) from Team as g)";
		Query query = getSession().createQuery(hql);
		
		return (Team) query.uniqueResult();
	}
}
