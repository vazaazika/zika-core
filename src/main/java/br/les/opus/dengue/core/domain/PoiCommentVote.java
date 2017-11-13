package br.les.opus.dengue.core.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.les.opus.dengue.core.json.View;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "vote_comment")
public class PoiCommentVote extends Vote {

	@ManyToOne
	@JsonView(View.Detail.class)
	@JoinColumn(name = "comment_id")
	private PoiComment comment;

	public PoiComment getComment() {
		return comment;
	}

	public void setComment(PoiComment comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "PoiCommentVote [comment=" + comment + ", isUpVote()="
				+ isUpVote() + ", getUser()=" + getUser() + "]";
	}
	
}
