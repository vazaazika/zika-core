package br.les.opus.dengue.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.les.opus.dengue.core.domain.Vote;
import br.les.opus.dengue.core.domain.Voteable;
import br.les.opus.dengue.core.factories.VoteRepositoryFactory;
import br.les.opus.dengue.core.repositories.VoteRepository;

@Service
public class VoteService {
	
	
	@Autowired
	private VoteRepositoryFactory voteRepositoryFactory;

	/**
	 * Votes a specific voteable. This method assumes the vote object is valid, i.e.,
	 * the user must be already specified
	 * @param voteable object to be voted
	 * @param vote vote to be processed
	 */
	public void vote(Voteable voteable, Vote vote) {
		synchronized (voteable) {
			VoteRepository voteRepository = voteRepositoryFactory.createByVoteable(voteable);
			Vote currentVote = voteRepository.findByVoteableAndUser(voteable, vote.getUser());
			
			/**
			 * If this entity has a vote, remove the current vote
			 * of the entity to process the new one
			 */
			if (currentVote != null) {
				voteable.removeVote(currentVote);
				vote.setId(currentVote.getId());
			}
			
			/**
			 * If this entity has a current vote and the new vote
			 * is the same, delete the current vote. It means
			 * if a vote exists and it is an up vote and the
			 * new one is also an up vote, no vote will longer
			 * exists
			 */
			if (currentVote != null && currentVote.isTheSameVote(vote)) {
				voteRepository.delete(currentVote);
			} else {
				/**
				 * Save the new vote an change the voted entity counters
				 */
				vote = voteRepository.save(vote);
				voteable.addVote(vote);
			}
			
		}
	}
}
