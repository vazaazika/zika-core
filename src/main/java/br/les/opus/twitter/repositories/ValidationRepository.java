package br.les.opus.twitter.repositories;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.commons.persistence.builders.CriteriaBuilder;
import br.les.opus.commons.persistence.filtering.Filter;
import br.les.opus.twitter.domain.Validation;

@Repository
public class ValidationRepository extends HibernateAbstractRepository<Validation, Long>{
	//TODO need to get lowest and highest tweet id for time period.
	
	
	@SuppressWarnings("unchecked")
	public List<Validation> findAllGeotagged() {
		return getSession().createQuery("from validation where geolocation is not null").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Validation> findAllUnclassifiedByLanguage(String lang) {
		String hql = "from Validation where lang = :lang and classification is null and toBeClassified = TRUE";
		Query query = getSession().createQuery(hql);
		query.setParameter("lang", lang);
		
		return query.list();
	}
	
	public Validation findById(Long id) {
		String hql = "from Validation where id = :id ";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", id);
		return (Validation)query.list().get(0);
	}
	
	public long findMaxId() {
		String hql = "select max(id) from Tweet";
		Query query = getSession().createQuery(hql);
		return (long) query.list().get(0);
	}
	
	public long findMinId(Date date) {
		String hql = "select min(id) from Tweet where createdAt >= :date";
		Query query = getSession().createQuery(hql);
		query.setParameter("date", date);
		return (long) query.list().get(0);
	}
	
	public void deleteOlderThan(Date date) {
		String hql = "delete from Validation where createdAt < :date";
		Query query = getSession().createQuery(hql);
		query.setParameter("date", date);
		System.out.println(query.executeUpdate());
	}
	
	@SuppressWarnings("unchecked")
	public List<Validation> findAllAfter(Date date) {
		String hql = "from validation where createdAt >= :date and lang = 'pt' ";
		Query query = getSession().createQuery(hql);
		query.setParameter("date", date);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Validation> findAllZika() {
		String hql = "from Validation where lang = 'pt' and (lower(text) like '%zika%' or lower(text) like '%chikungunya%') order by createdAt desc";
		Query query = getSession().createQuery(hql);
		query.setMaxResults(100000);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findAllScreenNames() {
		String hql = "select v.user.screenName from Validation v";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Validation> findAllGeotaggedPlain(Pageable pageable, Filter filter) {
		CriteriaBuilder builder = new CriteriaBuilder(getSession(), getEntityClass());
		if (pageable.getSort() != null) {
			builder.addSort(pageable.getSort());
		}
		if (filter != null) {
			builder.addFilter(filter);
		}
		Criteria criteria = builder.getBuiltCriteria();
		criteria.add(Restrictions.isNotNull("geolocation"));
		criteria.setFirstResult(pageable.getOffset());
		criteria.setMaxResults(pageable.getPageSize());
		List<Validation> content = getUniqueObjects(criteria.list());
		return content;
	}
}

