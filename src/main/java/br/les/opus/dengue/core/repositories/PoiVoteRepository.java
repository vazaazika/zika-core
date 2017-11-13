package br.les.opus.dengue.core.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.dengue.core.domain.PoiVote;
import br.les.opus.dengue.core.domain.PointOfInterest;
import br.les.opus.dengue.core.domain.Vote;
import br.les.opus.dengue.core.domain.Voteable;

@Repository
public class PoiVoteRepository extends HibernateAbstractRepository<PoiVote, Long> implements VoteRepository {

	@SuppressWarnings("unchecked")
	public PoiVote findByUserAndPoi(PointOfInterest poi, User user) {
		String hql = "from PoiVote pv where pv.poi.id = :poiId and pv.user.id = :idUser ";
		Query query = getSession().createQuery(hql);
		query.setParameter("idUser", user.getId());
		query.setParameter("poiId", poi.getId());
		List<PoiVote> votes =  query.list();
		if (votes.isEmpty()) {
			return null;
		} else {
			return votes.get(0);
		}
	}

	@Override
	public Vote findByVoteableAndUser(Voteable voteable, User user) {
		return findByUserAndPoi((PointOfInterest)voteable, user);
	}

	@Override
	public void delete(Vote vote) {
		this.delete(vote.getId());
	}

	@Override
	public Vote save(Vote vote) {
		return super.save((PoiVote)vote);
	}

}
