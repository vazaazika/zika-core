package br.les.opus.auth.core.repositories;

import org.hibernate.Criteria;
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
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("password", password));
		Object obj = criteria.uniqueResult();
		return (obj == null)? null : (User)obj;
	}
}
