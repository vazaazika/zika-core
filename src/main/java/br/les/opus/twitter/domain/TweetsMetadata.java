package br.les.opus.twitter.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.les.opus.commons.persistence.IdAware;

@Entity
@Table(name = "tweet_metadata", schema = "twitter", uniqueConstraints = {@UniqueConstraint(columnNames={"user_id","classification_id"})})
public class TweetsMetadata implements IdAware<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", schema = "twitter", sequenceName = "SQ_PK_TWEET_METADATA")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private TwitterUser user;
	
	@ManyToOne
	@JoinColumn(name = "classification_id")
	private TweetClassification classification;
	
	/**
	 * Counts how many tweets the user published that were classified in this category  
	 */
	private Long tweetsCount;
	
	/**
	 * Express the interest of the user in this category. It represents the percentage
	 * of all tweets published by this user in this category. 
	 * In the paper, it represents a cell of DT' matrix
	 */
	private Double interest;

	/**
	 * Express the participation of the user in this category when compared to other users.
	 * It represents the proportion of contribution of this user for all tweets in this category.
	 * In other words, what is the percentage of all tweets of this category that the user published.
	 * In the paper, it represents a cell of DT'' matrix
	 */
	private Double participation;
	
	/**
	 * Computed TwitterRank value for this user in this topic
	 */
	private Double twitterRank;
	
	public TweetsMetadata() {
		this.tweetsCount = 0l;
	}
	
	public TweetsMetadata(Tweet tweet) {
		this();
		this.user = tweet.getUser();
		this.classification = tweet.getClassification();
		this.tweetsCount = 1l;
	}
	
	public void incrementCount() {
		this.tweetsCount++;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getTweetsCount() {
		return tweetsCount;
	}

	public void setTweetsCount(Long tweetsCount) {
		this.tweetsCount = tweetsCount;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getParticipation() {
		return participation;
	}

	public void setParticipation(Double participation) {
		this.participation = participation;
	}

	public Double getTwitterRank() {
		return twitterRank;
	}

	public void setTwitterRank(Double twitterRank) {
		this.twitterRank = twitterRank;
	}

	@Override
	public String toString() {
		return "TweetsMetadata [id=" + id + ", user=" + user + ", classification=" + classification + ", tweetsCount="
				+ tweetsCount + ", interest=" + interest + ", participation=" + participation + ", twitterRank="
				+ twitterRank + "]";
	}
	
}



