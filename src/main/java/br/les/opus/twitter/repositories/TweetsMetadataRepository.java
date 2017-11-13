package br.les.opus.twitter.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.twitter.domain.TweetClassification;
import br.les.opus.twitter.domain.TweetsMetadata;
import br.les.opus.twitter.domain.TwitterUser;

@Repository
public class TweetsMetadataRepository extends HibernateAbstractRepository<TweetsMetadata, Long>{

	public Long countPublishedTweetsFrom(TwitterUser user) {
		String hql = "select sum(tweetsCount) from TweetsMetadata where user.id = :userId";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", user.getId());
		Object count = query.uniqueResult();
		if (count == null) {
			return 0l;
		}
		return (Long)count;
	}
	
	public Long countPublishedTweetsFrom(List<TwitterUser> users) {
		Long count = 0l;
		for (TwitterUser user : users) {
			count += this.countPublishedTweetsFrom(user);
		}
		return count;
	}
	
	public void updateAllParticipations() {
		String hql = "update TweetsMetadata tm set participation = "
				+ "tm.tweetsCount/(select sum(tweetsCount) from TweetsMetadata where classification.id = tm.classification.id)";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public void deleteAllParticipations() {
		String hql = "update TweetsMetadata set participation = null";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public void deleteAllInterests() {
		String hql = "update TweetsMetadata set interest = null";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public void updateAllInterests() {
		String hql = "update TweetsMetadata tm set interest = "
				+ "tm.tweetsCount/(select sum(tweetsCount) from TweetsMetadata where user.id = tm.user.id)";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public Long countPublishedByCategory(TweetClassification topic) {
		String hql = "select sum(tweetsCount) from TweetsMetadata where classification.id = :classificationId";
		Query query = getSession().createQuery(hql);
		query.setParameter("classificationId", topic.getId());
		Object count = query.uniqueResult();
		if (count == null) {
			return 0l;
		}
		return (Long)count;
	}
	
	public TweetsMetadata findOne(TwitterUser user, TweetClassification topic) {
		String hql = "from TweetsMetadata where user.id = :userId and classification.id = :topicId";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", user.getId());
		query.setParameter("topicId", topic.getId());
		Object result = query.uniqueResult();
		if (result == null) {
			return null;
		}
		return (TweetsMetadata)result;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findAllForValidation() {
		//TODO remove magic numbers
		String hql = "select tm.user.screenName from TweetsMetadata tm where tm.classification = 7 and tm.tweetsCount >= 3";
		Query query = getSession().createQuery(hql);
		query.setMaxResults(100000);
		return query.list();
	}
}
