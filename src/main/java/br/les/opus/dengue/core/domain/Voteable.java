package br.les.opus.dengue.core.domain;

import br.les.opus.commons.persistence.IdAware;

public interface Voteable extends IdAware<Long> {

	void addVote(Vote vote);

	void removeVote(Vote vote);

	void incrementUpVote();

	void decrementUpVote();

	void incrementDownVote();

	void decrementDownVote();

	Vote getUserVote();

	void setUserVote(Vote vote);
}
