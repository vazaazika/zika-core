package br.les.opus.instagram.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "comments_envelope", schema = "instagram")
public class CommentsEnvelope {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_COMMENTS_ENVELOPE", schema = "instagram")
	private Long id;

	private Integer count;

	@SerializedName("data")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name="comments_envelope_comment",
		schema = "instagram",
		joinColumns = @JoinColumn( name="comments_envelope_id"),
		inverseJoinColumns = @JoinColumn( name="comment_id")
	)
	private List<Comment> comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
