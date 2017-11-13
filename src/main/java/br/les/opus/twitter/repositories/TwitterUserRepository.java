package br.les.opus.twitter.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.twitter.domain.TweetClassification;
import br.les.opus.twitter.domain.TwitterUser;

@Repository
public class TwitterUserRepository extends HibernateAbstractRepository<TwitterUser, Long> {

	/**
	 * Find all users that published at least 1 tweet in one of the considered
	 * categories {@link TweetClassification}
	 * @return the list of all relevant users
	 */
	@SuppressWarnings("unchecked")
	public List<TwitterUser> findAllRelevant() {
		String hql = "select distinct u from TwitterUser u inner join u.tweetsMetadata m with m.tweetsCount >= 1 order by u.id";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	public void deleteByScreenname(String screenname) {
		String hql = "delete from TwitterUser where screenName = :screenname";
		Query query = getSession().createQuery(hql);
		query.setParameter("screenname", screenname);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<TwitterUser> findAllWithOutdatedFollowers() {
		String hql = "from TwitterUser where outdatedFollowers = true";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TwitterUser> findAllOrderedById() {
		String hql = "from TwitterUser order by id asc";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	public TwitterUser findByScreenname(String screenname) {
		String hql = "from TwitterUser order where screenName = :screenName";
		Query query = getSession().createQuery(hql);
		query.setParameter("screenName", screenname);
		return (TwitterUser)query.list().get(0);
	}
	
}
