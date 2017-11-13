package br.les.opus.twitter.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.twitter.domain.TweetClassification;

@Repository
public class TweetClassificationRepository extends HibernateAbstractRepository<TweetClassification, Long> {

	@SuppressWarnings("unchecked")
	public TweetClassification findByKey(String key) {
		String hql = "from TweetClassification where key = :key";
		Query query = getSession().createQuery(hql);
		query.setParameter("key", key);
		List<TweetClassification> list = query.list();
		if (list.isEmpty()) {
			return null;
		}
		return list.iterator().next();
	}
	
	@SuppressWarnings("unchecked")
	public List<TweetClassification> findAllUsedInTwitterRank() {
		String hql = "from TweetClassification where usedInTwitterRank = true";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
}
