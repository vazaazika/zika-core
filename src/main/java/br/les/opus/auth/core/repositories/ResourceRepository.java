package br.les.opus.auth.core.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.auth.core.domain.Resource;
import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.HibernateAbstractRepository;

@Repository
public class ResourceRepository  extends HibernateAbstractRepository<Resource, Long> {

	@SuppressWarnings("unchecked")
	public List<Resource> findAllByUser(User user) {
		String hql = "select distinct rsc from User u "
				+ "inner join u.userRoles ur "
				+ "inner join ur.role r "
				+ "inner join r.roleResources rscRoles "
				+ "inner join rscRoles.resource rsc "
				+ "where u.id = :idUser ";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("idUser", user.getId());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Resource> findAllOpen() {
		String hql = "from Resource where open = true";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
}
