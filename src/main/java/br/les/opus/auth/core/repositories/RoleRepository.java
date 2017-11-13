package br.les.opus.auth.core.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.auth.core.domain.Role;
import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.HibernateAbstractRepository;

@Repository
public class RoleRepository extends HibernateAbstractRepository<Role, Long> {

	@SuppressWarnings("unchecked")
	public List<Role> findAllByUser(User user) {
		String hql = "select distinct ur.role from UserRole ur where ur.user.id = :idUser";
		Query query = getSession().createQuery(hql);
		query.setParameter("idUser", user.getId());
		return query.list();
	}
	
	public Role findByAuthority(String authority) {
		String hql = "from Role where authority = :authority";
		Query query = getSession().createQuery(hql);
		query.setParameter("authority",  authority);
		Object obj = query.uniqueResult();
		if (obj != null) {
			return (Role)obj;
		}
		return null;
	}
	
}
