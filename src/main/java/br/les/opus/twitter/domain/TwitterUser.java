package br.les.opus.twitter.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "twitter_user", schema = "twitter")
public class TwitterUser {

	@Id
	private Long id;

	private String name;
	
	private String screenName;
	
	private String location;
	
	@JsonIgnore
	private String description;
	
	@JsonIgnore
	private Boolean isContributorsEnabled;

	private String profileImageUrl;
	
	private String profileImageUrlHttps;
	
	private String url;

	@JsonIgnore
	private Boolean isProtected;
	
	@JsonIgnore
	private Integer followersCount;

	@JsonIgnore
	private String profileBackgroundColor;

	@JsonIgnore
	private String profileTextColor;
	
	@JsonIgnore
	private String profileLinkColor;
	
	@JsonIgnore
	private String profileSidebarFillColor;
	
	@JsonIgnore
	private String profileSidebarBorderColor;
	
	@JsonIgnore
	private Boolean profileUseBackgroundImage;
	
	@JsonIgnore
	private Boolean showAllInlineMedia;
	
	@JsonIgnore
	private Integer friendsCount;
	
	@JsonIgnore
	private Date createdAt;
	
	@JsonIgnore
	private Integer favouritesCount;
	
	@JsonIgnore
	private Integer utcOffset;
	
	@JsonIgnore
	private String timeZone;
	
	@JsonIgnore
	private String profileBackgroundImageUrl;
	
	@JsonIgnore
	private String profileBackgroundImageUrlHttps;
	
	@JsonIgnore
	private String profileBannerImageUrl;
	
	@JsonIgnore
	private Boolean profileBackgroundTiled;
	
	@JsonIgnore
	private String lang;
	
	@JsonIgnore
	private Integer statusesCount;
	
	@JsonIgnore
	private Boolean isGeoEnabled;
	
	@JsonIgnore
	private Boolean isVerified;
	
	@JsonIgnore
	private Boolean translator;
	
	@JsonIgnore
	private Integer listedCount;
	
	@JsonIgnore
	private Boolean isFollowRequestSent;
	
	/**
	 * It indicates that we need to go to Twitter
	 * and get the followers of the user
	 */
	@Column(name = "outdated_followers")
	private Boolean outdatedFollowers;
	
	@OneToOne(mappedBy = "user")
	private ValidationMetadata validationMetadata;
	
	@JsonIgnore
	@ManyToMany  
    @JoinTable(name="follow", 
    	schema = "twitter",
    	joinColumns=@JoinColumn(name="following_id"), 
    	inverseJoinColumns=@JoinColumn(name="follower_id")
    )
	private List<TwitterUser> followers;
	
	@JsonIgnore
	@ManyToMany  
    @JoinTable(name="follow", 
    	schema = "twitter",
    	joinColumns=@JoinColumn(name="follower_id"), 
    	inverseJoinColumns=@JoinColumn(name="following_id")
    )
	private List<TwitterUser> following;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<TweetsMetadata> tweetsMetadata;
	
	public TwitterUser() {
		this.followers = new ArrayList<>();
		this.following = new ArrayList<>();
		this.setOutdatedFollowers(true);
	}
	
	public ValidationMetadata getValidationMetadata() {
		return validationMetadata;
	}

	public void setValidationMetadata(ValidationMetadata validation) {
		this.validationMetadata = validation;
	}

	public TwitterUser(Long id) {
		this();
		this.id = id;
	}
	
	public List<TweetsMetadata> getTweetsMetadata() {
		return tweetsMetadata;
	}

	public void setTweetsMetadata(List<TweetsMetadata> tweetsMetadata) {
		this.tweetsMetadata = tweetsMetadata;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsContributorsEnabled() {
		return isContributorsEnabled;
	}

	public void setIsContributorsEnabled(Boolean isContributorsEnabled) {
		this.isContributorsEnabled = isContributorsEnabled;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getProfileImageUrlHttps() {
		return profileImageUrlHttps;
	}

	public void setProfileImageUrlHttps(String profileImageUrlHttps) {
		this.profileImageUrlHttps = profileImageUrlHttps;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsProtected() {
		return isProtected;
	}

	public void setIsProtected(Boolean isProtected) {
		this.isProtected = isProtected;
	}

	public Integer getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}

	public String getProfileBackgroundColor() {
		return profileBackgroundColor;
	}

	public void setProfileBackgroundColor(String profileBackgroundColor) {
		this.profileBackgroundColor = profileBackgroundColor;
	}

	public String getProfileTextColor() {
		return profileTextColor;
	}

	public void setProfileTextColor(String profileTextColor) {
		this.profileTextColor = profileTextColor;
	}

	public String getProfileLinkColor() {
		return profileLinkColor;
	}

	public void setProfileLinkColor(String profileLinkColor) {
		this.profileLinkColor = profileLinkColor;
	}

	public String getProfileSidebarFillColor() {
		return profileSidebarFillColor;
	}

	public void setProfileSidebarFillColor(String profileSidebarFillColor) {
		this.profileSidebarFillColor = profileSidebarFillColor;
	}

	public String getProfileSidebarBorderColor() {
		return profileSidebarBorderColor;
	}

	public void setProfileSidebarBorderColor(String profileSidebarBorderColor) {
		this.profileSidebarBorderColor = profileSidebarBorderColor;
	}

	public Boolean getProfileUseBackgroundImage() {
		return profileUseBackgroundImage;
	}

	public void setProfileUseBackgroundImage(Boolean profileUseBackgroundImage) {
		this.profileUseBackgroundImage = profileUseBackgroundImage;
	}

	public Boolean getShowAllInlineMedia() {
		return showAllInlineMedia;
	}

	public void setShowAllInlineMedia(Boolean showAllInlineMedia) {
		this.showAllInlineMedia = showAllInlineMedia;
	}

	public Integer getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(Integer friendsCount) {
		this.friendsCount = friendsCount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getFavouritesCount() {
		return favouritesCount;
	}

	public void setFavouritesCount(Integer favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	public Integer getUtcOffset() {
		return utcOffset;
	}

	public void setUtcOffset(Integer utcOffset) {
		this.utcOffset = utcOffset;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getProfileBackgroundImageUrl() {
		return profileBackgroundImageUrl;
	}

	public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
		this.profileBackgroundImageUrl = profileBackgroundImageUrl;
	}

	public String getProfileBackgroundImageUrlHttps() {
		return profileBackgroundImageUrlHttps;
	}

	public void setProfileBackgroundImageUrlHttps(
			String profileBackgroundImageUrlHttps) {
		this.profileBackgroundImageUrlHttps = profileBackgroundImageUrlHttps;
	}

	public String getProfileBannerImageUrl() {
		return profileBannerImageUrl;
	}

	public void setProfileBannerImageUrl(String profileBannerImageUrl) {
		this.profileBannerImageUrl = profileBannerImageUrl;
	}

	public Boolean getProfileBackgroundTiled() {
		return profileBackgroundTiled;
	}

	public void setProfileBackgroundTiled(Boolean profileBackgroundTiled) {
		this.profileBackgroundTiled = profileBackgroundTiled;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Integer getStatusesCount() {
		return statusesCount;
	}

	public void setStatusesCount(Integer statusesCount) {
		this.statusesCount = statusesCount;
	}

	public Boolean getIsGeoEnabled() {
		return isGeoEnabled;
	}

	public void setIsGeoEnabled(Boolean isGeoEnabled) {
		this.isGeoEnabled = isGeoEnabled;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Boolean getTranslator() {
		return translator;
	}

	public void setTranslator(Boolean translator) {
		this.translator = translator;
	}

	public Integer getListedCount() {
		return listedCount;
	}

	public void setListedCount(Integer listedCount) {
		this.listedCount = listedCount;
	}

	public Boolean getIsFollowRequestSent() {
		return isFollowRequestSent;
	}

	public void setIsFollowRequestSent(Boolean isFollowRequestSent) {
		this.isFollowRequestSent = isFollowRequestSent;
	}
	
	public List<TwitterUser> getFollowers() {
		return followers;
	}

	public void setFollowers(List<TwitterUser> followers) {
		this.followers = followers;
	}

	public List<TwitterUser> getFollowing() {
		return following;
	}

	public void setFollowing(List<TwitterUser> following) {
		this.following = following;
	}
	
	public Boolean getOutdatedFollowers() {
		return outdatedFollowers;
	}

	public void setOutdatedFollowers(Boolean outdatedFollowers) {
		this.outdatedFollowers = outdatedFollowers;
	}

	@Override
	public String toString() {
		return "TwitterUser [id=" + id + ", name=" + name + "]";
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
		TwitterUser other = (TwitterUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
