package br.les.opus.gamification.repositories;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.auth.core.domain.Resource;
import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Task;

@Repository
public class TaskRepository extends HibernateAbstractRepository<Task, Long>{

	public Task findByResource(Resource resource) {
		String hql = "from Task where resource.id = :resourceId";
		Query query = getSession().createQuery(hql);
		query.setParameter("resourceId", resource.getId());
		Object obj = query.uniqueResult();
		if (obj == null) {
			return null;
		}
		return (Task)obj;
	}
}
