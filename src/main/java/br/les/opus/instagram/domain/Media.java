package br.les.opus.instagram.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "media", schema = "instagram")
public class Media {

	@Id
	private String id;

	private String type;

	private String filter;

	@ElementCollection
	@CollectionTable(name="media_tag", schema = "instagram",  joinColumns = @JoinColumn(name="media_id"))
	@JsonIgnore
	@Column(name="tag")
	private List<String> tags;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	@JoinColumn(name = "comments_envelope_id")
	private CommentsEnvelope comments;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "caption_id")
	private Comment caption;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	@JoinColumn(name = "likes_envelope_id")
	private LikesEnvelope likes;
	
	private String link;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private InstagramUser user;
	
	@SerializedName("created_time")
	private Date createdTime;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "picture_pool_id")
	private PicturePool images;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "video_pool_id")
	private VideoPool videos;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "location_id")
	private Location location;
	
	@SerializedName("users_in_photo")
	@JsonIgnore
	@OneToMany(mappedBy = "media", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<UserInPhoto> taggedUsers;
	
	@Transient
	public boolean isValid() {
		if (images == null && videos == null) {
			return false;
		}
		if (images != null && !images.isValid()) {
			return false;
		}
		if (videos != null && !videos.isValid()) {
			return false;
		}
		return true; 
	}
	
	public void replaceImages(PicturePool oldPool) {
		if (this.images == null) {
			return;
		}
		images.setId(oldPool.getId());
		images.getLowResolution().setId(oldPool.getLowResolution().getId());
		images.getStandardResolution().setId(oldPool.getStandardResolution().getId());
		images.getThumbnail().setId(oldPool.getThumbnail().getId());
	}
	
	public void replaceVideos(VideoPool oldVideoPool) {
		if (this.videos == null) {
			return;
		}
		videos.setId(oldVideoPool.getId());
		videos.getLowResolution().setId(oldVideoPool.getLowResolution().getId());
		videos.getStandardResolution().setId(oldVideoPool.getStandardResolution().getId());
	}
	
	public List<UserInPhoto> getTaggedUsers() {
		return taggedUsers;
	}

	public void setTaggedUsers(List<UserInPhoto> taggedUsers) {
		this.taggedUsers = taggedUsers;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public CommentsEnvelope getComments() {
		return comments;
	}

	public void setComments(CommentsEnvelope comments) {
		this.comments = comments;
	}

	public Comment getCaption() {
		return caption;
	}

	public void setCaption(Comment caption) {
		this.caption = caption;
	}

	public LikesEnvelope getLikes() {
		return likes;
	}

	public void setLikes(LikesEnvelope likes) {
		this.likes = likes;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public PicturePool getImages() {
		return images;
	}

	public void setImages(PicturePool images) {
		this.images = images;
	}

	public VideoPool getVideos() {
		return videos;
	}

	public void setVideos(VideoPool videos) {
		this.videos = videos;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
}






