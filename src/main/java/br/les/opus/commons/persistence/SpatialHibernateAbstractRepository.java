package br.les.opus.commons.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.vividsolutions.jts.geom.Point;

import br.les.opus.commons.persistence.spatial.DistanceResult;
import br.les.opus.commons.persistence.spatial.GeotaggedObject;

public abstract class SpatialHibernateAbstractRepository<T extends GeotaggedObject, K extends Serializable> 
	extends HibernateAbstractRepository<T, K> {

	@SuppressWarnings("unchecked")
	public Page<DistanceResult> findAllOrderringByDistance(Point origin, Pageable pageable) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select  ");
		buffer.append("g, distance(g.location, :origin )  ");
		buffer.append("from ");
		buffer.append(getEntityClass().getName());
		buffer.append(" g order by distance( g.location, :origin ) ");
		
		Query query = getSession().createQuery(buffer.toString());
		query.setParameter("origin", origin.toText());
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		
		List<Object[]> rawResults = query.list();
		List<DistanceResult> content = new ArrayList<>();
		for (Object[] objects : rawResults) {
			DistanceResult result = new DistanceResult();
			result.setObject((T)objects[0]);
			result.setDistance((Double)objects[1]);
		}
		
		PageImpl<DistanceResult> page = new PageImpl<>(content, pageable, this.count());
		return page;
	}
	
}
