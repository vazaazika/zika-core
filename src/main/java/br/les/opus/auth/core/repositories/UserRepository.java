package br.les.opus.auth.core.repositories;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.HibernateAbstractRepository;

@Repository
public class UserRepository extends HibernateAbstractRepository<User, Long> {

	public User findByUsername(String username) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq("username", username).ignoreCase());
		Object obj = criteria.uniqueResult();
		return (obj == null)? null : (User)obj;
	}
	
	public User findByUsernameAndPassword(String username, String password) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq("username", username).ignoreCase());
		criteria.add(Restrictions.eq("password", password));
		Object obj = criteria.uniqueResult();
		return (obj == null)? null : (User)obj;
	}

	public User findUserByInvitationToken (String token){
		String hql = "select system_user.username from game_invite " +
				"inner join system_user on game_invite.id = system_user.invite_id where game_invite.hashedtoken = " + token;

		Query query = getSession().createQuery(hql);
		Object obj = query.uniqueResult();

		if (obj == null) {
			return null;
		}
		return (User)obj;



	}

	/*
	String hql = "from Task where resource.id = :resourceId";
		Query query = getSession().createQuery(hql);
		query.setParameter("resourceId", resource.getId());
		Object obj = query.uniqueResult();
		if (obj == null) {
			return null;
		}
		return (Task)obj;
	 */

	/*
	StringBuilder builder = new StringBuilder();
		builder.append("select distinct p, count(u) from Player p");
		builder.append(" left join p.poiUpdates as u with u.type.id = :verificationId ");
		builder.append("group by  ");
		builder.append(" p.id, ");
		builder.append(" p.avatar, ");
		builder.append(" p.enabled, ");
		builder.append(" p.locked, ");
		builder.append(" p.name, ");
		builder.append(" p.password, ");
		builder.append(" p.username, ");
		builder.append(" p.version, ");
		builder.append(" p.level, ");
		builder.append(" p.nickname, ");
		builder.append(" p.xp ");
		builder.append("order by count(u) desc ");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("verificationId", PoiStatusUpdateType.VERIFICATION);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<RankedPlayer> players = new ArrayList<>();
		List<Object[]> result = query.list();
		for (Object[] objects : result) {
			Player player = (Player)objects[0];
			Long count = (Long)objects[1];
			players.add(new RankedPlayer(player, count));
		}

		return new PageImpl<>(players, pageable, this.count());
	 */
}
