package br.les.opus.gamification.repositories;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Point;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.IBGEInfo;

@Repository
public class IBGERepository extends HibernateAbstractRepository<IBGEInfo, Long>{
	
	public IBGEInfo findByPoint(Point point) {
		IBGEInfo ibge = new IBGEInfo();
		
		String x = Double.toString(point.getX());
		String y = Double.toString(point.getY());
		
		String hql = "select nome from ibge where ST_CONTAINS(ibge.geom, ST_GeomFromText('POINT(" + x + " " + y + ")', 4326))";
		SQLQuery query = getSession().createSQLQuery(hql);
		

		
		ibge.setNome((String) query.uniqueResult());
		
		//hql = "select nome from ibge where ST_CONTAINS(ibge.geom, ST_GeomFromText('POINT(" + point.getX()+ " " + point.getY() + ")', 4326))";
		hql = "select uf from ibge where ST_CONTAINS(ibge.geom, ST_GeomFromText('POINT(" + x + " " + y + ")', 4326))";
		query = getSession().createSQLQuery(hql);
		
		ibge.setUf((String) query.uniqueResult());
		
		return ibge;
	}

}
