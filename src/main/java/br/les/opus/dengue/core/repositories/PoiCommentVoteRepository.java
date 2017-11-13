package br.les.opus.dengue.core.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.dengue.core.domain.PoiComment;
import br.les.opus.dengue.core.domain.PoiCommentVote;
import br.les.opus.dengue.core.domain.PointOfInterest;
import br.les.opus.dengue.core.domain.Vote;
import br.les.opus.dengue.core.domain.Voteable;

@Repository
public class PoiCommentVoteRepository extends HibernateAbstractRepository<PoiCommentVote, Long> implements VoteRepository {

	@SuppressWarnings("unchecked")
	public List<PoiCommentVote> findAllVotedByUserAndPoiOrderedById(PointOfInterest poi, User user) {
		String hql = "from PoiCommentVote pcv where "
				+ "pcv.comment.poi.id = :idPoi and pcv.user.id = :idUser order by pcv.comment.id";
		Query query = getSession().createQuery(hql);
		query.setParameter("idUser", user.getId());
		query.setParameter("idPoi", poi.getId());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public PoiCommentVote findByUserAndComment(PoiComment comment, User user) {
		String hql = "from PoiCommentVote pcv where "
				+ "pcv.comment.id = :commentId and pcv.user.id = :idUser order by pcv.comment.id";
		Query query = getSession().createQuery(hql);
		query.setParameter("idUser", user.getId());
		query.setParameter("commentId", comment.getId());
		List<PoiCommentVote> votes =  query.list();
		if (votes.isEmpty()) {
			return null;
		} else {
			return votes.get(0);
		}
	}

	@Override
	public Vote findByVoteableAndUser(Voteable voteable, User user) {
		PoiComment comment = new PoiComment();
		comment.setId(voteable.getId());
		return findByUserAndComment(comment, user);
	}

	@Override
	public void delete(Vote vote) {
		this.delete(vote.getId());
	}

	@Override
	public Vote save(Vote vote) {
		return super.save((PoiCommentVote)vote);
	}

}
