package br.les.opus.gamification.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Membership;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Team;

@Repository
public class MembershipRepository extends HibernateAbstractRepository<Membership, Long>{

	public int deactivateAll(Player player) {
		String hql = "update Membership set active = false where player.id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", player.getId());
		return query.executeUpdate();
	}
	
	public int deactivate(Player player, Team team) {
		String hql = "update Membership set active = false where player.id = :id and team.id = :tId";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", player.getId());
		query.setParameter("tId", team.getId());
		return query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Membership> findAllActive(Player player, Team team) {
		String hql = "from Membership where player.id = :pId and team.id = :tId and active = true";
		Query query = getSession().createQuery(hql);
		query.setParameter("pId", player.getId());
		query.setParameter("tId", team.getId());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Membership> findAllActive(Player player) {
		String hql = "from Membership where player.id = :pId and active = true";
		Query query = getSession().createQuery(hql);
		query.setParameter("pId", player.getId());
		return query.list();
	}
}
