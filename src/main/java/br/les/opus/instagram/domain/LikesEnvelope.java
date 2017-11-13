package br.les.opus.instagram.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "likes_envelope", schema = "instagram")
public class LikesEnvelope {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_LIKES_ENVELOPE", schema = "instagram")
	private Long id;
	
	private Long count;
	
	@SerializedName("data")
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name="likes_envelope_likers",
		schema = "instagram",
		joinColumns = @JoinColumn( name="likes_envelope_id"),
		inverseJoinColumns = @JoinColumn( name="user_id")
	)
	private List<InstagramUser> likers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<InstagramUser> getLikers() {
		return likers;
	}

	public void setLikers(List<InstagramUser> likers) {
		this.likers = likers;
	}
	
}
