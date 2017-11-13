package br.les.opus.twitter.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tweet_classification", schema = "twitter")
public class TweetClassification {

	@Id
	private Long id;
	
	@Column(unique = true)
	private String key;

	private String label;
	
	private String description;
	
	@Column(name = "used_in_twitter_rank")
	private Boolean usedInTwitterRank;
	
	public TweetClassification() {

	}
	
	public TweetClassification(Long id) {
		super();
		this.id = id;
	}

	public boolean isRelevant() {
		return usedInTwitterRank;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Boolean getUsedInTwitterRank() {
		return usedInTwitterRank;
	}
	
	public Boolean isUsedInTwitterRank() {
		return usedInTwitterRank;
	}

	public void setUsedInTwitterRank(Boolean usedInTwitterRank) {
		this.usedInTwitterRank = usedInTwitterRank;
	}

	@Override
	public String toString() {
		return "TweetClassification [id=" + id + ", key=" + key + "]";
	}

}
