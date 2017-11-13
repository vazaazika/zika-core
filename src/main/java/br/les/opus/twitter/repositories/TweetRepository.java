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
import br.les.opus.twitter.domain.Tweet;

@Repository
public class TweetRepository extends HibernateAbstractRepository<Tweet, Long>{

	@SuppressWarnings("unchecked")
	public List<Tweet> findAllGeotagged() {
		return getSession().createQuery("from Tweet where geolocation is not null").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tweet> findAllUnclassifiedByLanguage(String lang) {
		String hql = "from Tweet where lang = :lang and classification is null";
		Query query = getSession().createQuery(hql);
		query.setParameter("lang", lang);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tweet> findAllByLanguage(String lang) {
		String hql = "from Tweet where lang = :lang ";
		Query query = getSession().createQuery(hql);
		query.setParameter("lang", lang);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tweet> findAllAfter(Date date) {
		String hql = "from Tweet where createdAt >= :date and lang = 'pt' ";
		Query query = getSession().createQuery(hql);
		query.setParameter("date", date);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tweet> findAllZika() {
		String hql = "from Tweet where lang = 'pt' and (lower(text) like '%zika%' or lower(text) like '%chikungunya%') order by createdAt desc";
		Query query = getSession().createQuery(hql);
		query.setMaxResults(100000);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tweet> findAllGeotaggedPlain(Pageable pageable, Filter filter) {
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
		List<Tweet> content = getUniqueObjects(criteria.list());
		return content;
	}
}
