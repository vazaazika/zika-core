package br.les.opus.dengue.core.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.les.opus.dengue.core.domain.PoiComment;
import br.les.opus.dengue.core.domain.PointOfInterest;
import br.les.opus.dengue.core.domain.Voteable;
import br.les.opus.dengue.core.repositories.PoiCommentVoteRepository;
import br.les.opus.dengue.core.repositories.PoiVoteRepository;
import br.les.opus.dengue.core.repositories.VoteRepository;

@Component
public class VoteRepositoryFactory {

	@Autowired
	private PoiCommentVoteRepository commentVoteRepository;
	
	@Autowired
	private PoiVoteRepository poiVoteRepository;
	
	public VoteRepository createByVoteable(Voteable voteable) {
		if (voteable instanceof PoiComment) {
			return commentVoteRepository;
		} else if (voteable instanceof PointOfInterest) {
			return poiVoteRepository;
		}
		return null;
	}
}
