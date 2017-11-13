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

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.les.opus.commons.persistence.IdAware;

@Entity
@Table(name = "validation_metadata", schema = "twitter", uniqueConstraints = {@UniqueConstraint(columnNames={"user_id"})})
public class ValidationMetadata implements IdAware<Long>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", schema = "twitter", sequenceName = "SQ_PK_TWEET_METADATA")
	private Long id;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "user_id")
	private TwitterUser user;
	
	/**
	 * Counts how many tweets the user had made in the requested time period 
	 */
	private Long tweetsCount;
	
	
	/**
	 * number of keyword related tweets in the given time period
	 */
	private Long zikaCount;
	
	/**
	 * number of tweets classified as relevant from time period
	 */
	private Long relevantCount;
	
	/**
	 * number of tweets classified as news from time period
	 */
	private Long newsCount;
	
	/**
	 * number of tweets classified as noise from time period
	 */
	private Long noiseCount;
	
	/**
	 * percentage of tweets classified as relevant against total tweets of user
	 */
	private Double topicFocus;
	
	/**
	 * percentage of tweets classified as relevant against total tweets of user
	 */
	private Double overallFocus;
	
	/**
	 * percentage of tweets containing keywords against total tweets of user
	 */
	private Double recallOverAll;
	

	public Double getTopicFocus() {
		return topicFocus;
	}

	public void setTopicFocus(Double topicFocus) {
		this.topicFocus = topicFocus;
	}

	public Double getOverallFocus() {
		return overallFocus;
	}

	public void setOverallFocus(Double overallFocus) {
		this.overallFocus = overallFocus;
	}

	public Double getRecallOverAll() {
		return recallOverAll;
	}

	public void setRecallOverAll(Double recallOverAll) {
		this.recallOverAll = recallOverAll;
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

	public Long getTweetsCount() {
		return tweetsCount;
	}

	public void setTweetsCount(Long tweetsCount) {
		this.tweetsCount = tweetsCount;
	}

	public Long getZikaCount() {
		return zikaCount;
	}

	public void setZikaCount(Long zikaCount) {
		this.zikaCount = zikaCount;
	}

	public Long getRelevantCount() {
		return relevantCount;
	}

	public void setRelevantCount(Long relevantCount) {
		this.relevantCount = relevantCount;
	}

	public Long getNewsCount() {
		return newsCount;
	}

	public void setNewsCount(Long newsCount) {
		this.newsCount = newsCount;
	}

	public Long getNoiseCount() {
		return noiseCount;
	}

	public void setNoiseCount(Long noiseCount) {
		this.noiseCount = noiseCount;
	}

	

//	@Override
//	public String toString() {
//		return "TweetsMetadata [id=" + id + ", user=" + user + ", classification=" + classification + ", tweetsCount="
//				+ tweetsCount + ", interest=" + interest + ", participation=" + participation + ", twitterRank="
//				+ twitterRank + "]";
//	}
	
}




