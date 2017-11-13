package br.les.opus.dengue.core.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.les.opus.auth.core.domain.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "poi_comment")
@JsonInclude(Include.NON_NULL)
public class PoiComment implements Comparable<PoiComment>, Voteable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_COMMENT")
	private Long id;
	
	private Date date;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "poi_id")
	private PointOfInterest poi;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@NotNull
	@Length(max = 2000, min = 2)
	@Column(nullable = false, length = 2000)
	private String text;
	
	private Integer upVoteCount;
	
	private Integer downVoteCount;
	
	@Transient
	private Vote userVote;
	
	public PoiComment() {
		this.date = new Date();
		this.upVoteCount = 0;
		this.downVoteCount = 0;
	}
	
	public void findUserVote(List<PoiCommentVote> userVotes) {
		for (PoiCommentVote vote : userVotes) {
			if (vote.getComment().equals(this)) {
				this.userVote = vote;
				return;
			}
		}
	}
	
	public void removeVote(Vote vote) {
		if (vote.isUpVote()) {
			this.decrementUpVote();
		} else {
			this.decrementDownVote();
		}
	}
	
	public void addVote(Vote vote) {
		if (vote.isUpVote()) {
			this.incrementUpVote();
		} else {
			this.incrementDownVote();
		}
	}
	
	public void incrementUpVote() {
		this.upVoteCount++;
	}
	
	public void decrementUpVote() {
		this.upVoteCount--;
	}
	
	public void incrementDownVote() {
		this.downVoteCount++;
	}
	
	public void decrementDownVote() {
		this.downVoteCount--;
	}
	
	@Transient
	@JsonIgnore
	public boolean isOwnedBy(User user) {
		return this.user.equals(user);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PointOfInterest getPoi() {
		return poi;
	}

	public void setPoi(PointOfInterest poi) {
		this.poi = poi;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getUpVoteCount() {
		return upVoteCount;
	}

	public void setUpVoteCount(Integer upVoteCount) {
		this.upVoteCount = upVoteCount;
	}

	public Integer getDownVoteCount() {
		return downVoteCount;
	}

	public void setDownVoteCount(Integer downVoteCount) {
		this.downVoteCount = downVoteCount;
	}

	public Vote getUserVote() {
		return userVote;
	}

	public void setUserVote(Vote userVote) {
		this.userVote = userVote;
	}

	@Override
	public int compareTo(PoiComment o) {
		return this.id.compareTo(o.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PoiComment other = (PoiComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
