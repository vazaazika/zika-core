package br.les.opus.auth.core.repositories;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.les.opus.auth.core.domain.User;
import br.les.opus.auth.core.domain.Token;
import br.les.opus.commons.persistence.HibernateAbstractRepository;

@Repository
public class TokenRepository extends HibernateAbstractRepository<Token, Long> {

	public void deleteAllFromUser(User user) {
		String hql = "delete from Token where user.id = :userId";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", user.getId());
		query.executeUpdate();
	}
	
	public void deleteUsedBefore(User user, Date date) {
		String hql = "delete from Token where longLasting = false and lastTimeUsed <= :date and user.id = :userId";
		Query query = getSession().createQuery(hql);
		query.setParameter("date", date);
		query.setParameter("userId", user.getId());
		query.executeUpdate();
	}
	
	public Token findByHash(String hash) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq("hashedToken", hash));
		Object obj = criteria.uniqueResult();
		return (obj == null)? null : (Token)obj;
	}
	
}
