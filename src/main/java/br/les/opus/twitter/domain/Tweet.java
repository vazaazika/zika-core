package br.les.opus.twitter.domain;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import br.les.opus.commons.persistence.IdAware;
import twitter4j.GeoLocation;

@Entity
@Table(name = "tweet", schema = "twitter")
public class Tweet implements IdAware<Long> {
	
	@Id
	private Long id;
	
	private Date createdAt;
	
	@JsonIgnore
	private int favoriteCount;
	
	@Column(columnDefinition="Geometry")
	@Type(type="org.hibernate.spatial.GeometryType")
	private Point geolocation;
	
	@JsonIgnore
	private String inReplyToScreenName;
	
	@JsonIgnore
	private Long inReplyToStatusId;
	
	@JsonIgnore
	private Long inReplyToUserId;
	
	@JsonIgnore
	private String lang;
	
	@JsonIgnore
	private Integer retweetCount;
	
	private String text;
	
	@JsonIgnore
	private String source;
	
	@JsonIgnore
	private Boolean favorited;
	
	@JsonIgnore
	private Boolean possiblySensitive;
	
	@JsonIgnore
	private Boolean retweet;
	
	@JsonIgnore
	private Boolean retweeted;
	
	@JsonIgnore
	private Boolean isTruncated;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private TwitterUser user;
	
	@ManyToOne
	@JoinColumn(name = "classification_id")
	private TweetClassification classification;
	
	@Transient
	public void setGeolocation(GeoLocation geolocation) {
		Coordinate coord = new Coordinate(geolocation.getLongitude(), geolocation.getLatitude());
		Point point = new GeometryFactory().createPoint(coord);
		this.setGeolocation(point);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}

	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}

	public Long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public void setInReplyToStatusId(Long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	public Long getInReplyToUserId() {
		return inReplyToUserId;
	}

	public void setInReplyToUserId(Long inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Integer getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(Integer retweetCount) {
		this.retweetCount = retweetCount;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Boolean getFavorited() {
		return favorited;
	}

	public void setFavorited(Boolean favorited) {
		this.favorited = favorited;
	}

	public Boolean getPossiblySensitive() {
		return possiblySensitive;
	}

	public void setPossiblySensitive(Boolean possiblySensitive) {
		this.possiblySensitive = possiblySensitive;
	}

	public Boolean getRetweet() {
		return retweet;
	}

	public void setRetweet(Boolean retweet) {
		this.retweet = retweet;
	}

	public Boolean getRetweeted() {
		return retweeted;
	}

	public void setRetweeted(Boolean retweeted) {
		this.retweeted = retweeted;
	}

	public Boolean getIsTruncated() {
		return isTruncated;
	}

	public void setIsTruncated(Boolean isTruncated) {
		this.isTruncated = isTruncated;
	}

	public Point getGeolocation() {
		return geolocation;
	}

	public void setGeolocation(Point geolocation) {
		this.geolocation = geolocation;
	}

	public TwitterUser getUser() {
		return user;
	}

	public void setUser(TwitterUser user) {
		this.user = user;
	}
	
	public TweetClassification getClassification() {
		return classification;
	}

	public void setClassification(TweetClassification classification) {
		this.classification = classification;
	}

	@Override
	public String toString() {
		return "Tweet [id=" + id + ", createdAt=" + createdAt + ", text="
				+ text + ", user=" + user + "]";
	}
	

}
