package br.les.opus.dengue.core.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.dengue.core.domain.PoiStatusUpdate;
import br.les.opus.dengue.core.domain.PoiStatusUpdateType;
import br.les.opus.dengue.core.domain.PointOfInterest;

@Repository
public class PoiStatusUpdateRepository extends HibernateAbstractRepository<PoiStatusUpdate, Long>{

	@SuppressWarnings("unchecked")
	public List<PoiStatusUpdate> findByUserAndPoi(User user, PointOfInterest poi, PoiStatusUpdateType type) {
		String hql = "from PoiStatusUpdate where poi.id = :poiId and type.id = :tId and user.id = :uId ";
		Query query = getSession().createQuery(hql);
		query.setParameter("poiId", poi.getId());
		query.setParameter("tId", type.getId());
		query.setParameter("uId", user.getId());
		return query.list();
	}



	//public PoiStatusUpdate updateStatusReportedToInalysis(User user, PointOfInterest poi, PoiStatusUpdateType type) {
	//	String hql = "UPDATE PoiStatusUpdate SET type_id = :tId";
	//	Query query = getSession().createQuery(hql);
	//	query.setParameter("poiId", poi.getId());
	//	query.setParameter("tId", type.getId());
	//	query.setParameter("uId", user.getId());
	//	return query.executeUpdate();
//	}



}
