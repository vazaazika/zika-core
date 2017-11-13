package br.les.opus.instagram.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "comment", schema = "instagram")
public class Comment {

	@Id
	private Long id;
	
	@JsonIgnore
	@SerializedName("from")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private InstagramUser user;
	
	@SerializedName("created_time")
	private Date createdTime;
	
	@Type(type="text")
	private String text;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InstagramUser getUser() {
		return user;
	}

	public void setUser(InstagramUser user) {
		this.user = user;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
