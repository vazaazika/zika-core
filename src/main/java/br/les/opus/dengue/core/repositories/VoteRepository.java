package br.les.opus.dengue.core.repositories;

import br.les.opus.auth.core.domain.User;
import br.les.opus.dengue.core.domain.Vote;
import br.les.opus.dengue.core.domain.Voteable;

public interface VoteRepository {
	Vote findByVoteableAndUser(Voteable voteable, User user); 
	
	void delete(Vote vote);
	
	Vote save(Vote vote);
}
