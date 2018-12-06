package br.les.opus.dengue.core.repositories;

import java.util.ArrayList;
import java.util.List;

import br.les.opus.gamification.domain.DashboardResults;
import br.les.opus.gamification.domain.HealthAgent;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Point;

import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.SpatialHibernateAbstractRepository;
import br.les.opus.commons.persistence.builders.CriteriaBuilder;
import br.les.opus.commons.persistence.spatial.DistanceResult;
import br.les.opus.dengue.core.domain.PointOfInterest;

@Repository
public class PointOfInterestRepository extends SpatialHibernateAbstractRepository<PointOfInterest, Long>{

	@SuppressWarnings("unchecked")
	public Page<DistanceResult> findAllOrderringByDistance(Point origin, Pageable pageable) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select  ");
		buffer.append("g, distance(g.location, :origin )  ");
		buffer.append("from ");
		buffer.append(getEntityClass().getName());
		buffer.append(" as g ");
		buffer.append(" left join fetch g.user ");
		buffer.append(" left join fetch g.type ");
		buffer.append(" order by distance( g.location, :origin ) ");

		Query query = getSession().createQuery(buffer.toString());
		query.setParameter("origin", origin.toText());
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<Object[]> rawResults = query.list();
		List<DistanceResult> content = new ArrayList<>();
		for (Object[] objects : rawResults) {
			DistanceResult result = new DistanceResult();
			result.setObject((PointOfInterest)objects[0]);
			result.setDistance((Double)objects[1]);
			content.add(result);
		}

		PageImpl<DistanceResult> page = new PageImpl<>(content, pageable, this.count());
		return page;
	}

	@SuppressWarnings("unchecked")
	public Page<PointOfInterest> findAllByUser(User user, Pageable pageable) {
		CriteriaBuilder builder = new CriteriaBuilder(getSession(), PointOfInterest.class);
		if (pageable.getSort() != null) {
			builder.addSort(pageable.getSort());
		}

		Criteria criteria = builder.getBuiltCriteria();
		criteria.createAlias("user", "u");
		criteria.add(Restrictions.eq("u.id", user.getId()));
		criteria.setFirstResult(pageable.getOffset());
		criteria.setMaxResults(pageable.getPageSize());

		List<PointOfInterest> content = getUniqueObjects(criteria.list());

		PageImpl<PointOfInterest> page = new PageImpl<>(content, pageable, this.count());
		return page;
	}




	public DashboardResults findAllPoiByFilters(HealthAgent agent, PointOfInterest point, Pageable pageable) {

		CriteriaBuilder builder = new CriteriaBuilder(getSession(), PointOfInterest.class);
		if (pageable.getSort() != null) {
			builder.addSort(pageable.getSort());
		}

		Criteria criteria = builder.getBuiltCriteria();

		System.out.println(point.getDate().getMonth());

		if (point.getDate() != null)
			criteria.add(Restrictions.sqlRestriction("month(this_.date) = "+point.getDate().getMonth()+""));

		if (point.getPoiStatusUpdate() != null)
			criteria.add(Restrictions.eq("poiStatusUpdate", point.getPoiStatusUpdate()));

		if (point.getType() != null)
			criteria.add(Restrictions.eq("type", point.getType()));

		if(point.getCity()!=null)
			criteria.add(Restrictions.eq("city", point.getCity()));

		if(point.getState()!=null)
			criteria.add(Restrictions.eq("state", point.getState()));

		criteria.setFirstResult(pageable.getOffset());
		criteria.setMaxResults(pageable.getPageSize());

		List<PointOfInterest> content = getUniqueObjects(criteria.list());

		//adicionar criteria de lugar
		criteria.add(Restrictions.eq("state", agent.getState()));
		criteria.add(Restrictions.eq("city", agent.getCity()));

		List<PointOfInterest> content2 = getUniqueObjects(criteria.list());

		//obtendo quantidade de todos
		long qtd = (long) getSession().createCriteria(PointOfInterest.class).setProjection(Projections.rowCount()).uniqueResult();

		PageImpl<PointOfInterest> page = new PageImpl<>(content, pageable, this.count());

		DashboardResults dbr = new DashboardResults();

		dbr.setTotalReportBase(qtd);
		dbr.setPointOfInterestPage(page);
		dbr.setPercentByStates(content2.size());

		return dbr;
	}


}