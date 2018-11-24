/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.les.opus.twitter.repositories;

import br.les.opus.test.util.DbTestUtil;
import br.les.opus.twitter.domain.Tweet;
import br.les.opus.twitter.domain.TweetClassification;
import br.les.opus.twitter.domain.TwitterUser;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import twitter4j.GeoLocation;

/**
 *
 * @author edge
 */
public class TweetRepositoryTest extends DbTestUtil {
	
	@Autowired
	private TweetRepository tweetRepository;
	
	@Autowired
	private TwitterUserRepository twitterUserRepository;
	
	@Autowired
	private TweetsMetadataRepository tweetMetadataRepository;
	
	@Autowired
	private TweetClassificationRepository classificationRepository;
			
	public TweetRepositoryTest() {
	}
	
	@Before
	public void setUp() {
		TwitterUser sampleUser = createUser(1L, "Marcio", "marcioaguiar", 5);
		
		twitterUserRepository.save(Arrays.asList(
				sampleUser,
				createUser(2L, "Luiz", "luizbarroca", 2),
				createUser(3L, "Diogo", "diogocarvalho", 1),
				createUser(4L, "Diego", "diegocedrim", 0)
		));
		
		sampleUser.setFollowers(Arrays.asList(
				twitterUserRepository.findByScreenname("luizbarroca"),
				twitterUserRepository.findByScreenname("diogocarvalho")
		));
		
		sampleUser.setFollowersCount(2);
		sampleUser.setFollowing(Arrays.asList(twitterUserRepository.findByScreenname("diogocarvalho")));
		sampleUser.setFollowersCount(1);
		sampleUser.setFriendsCount(4);
		sampleUser.setIsContributorsEnabled(true);
		sampleUser.setIsFollowRequestSent(true);
		sampleUser.setIsGeoEnabled(true);
		sampleUser.setIsProtected(true);
		sampleUser.setIsVerified(true);
		sampleUser.setLang("pt");
		sampleUser.setListedCount(20);
		sampleUser.setLocation("Maceió");
		sampleUser.setOutdatedFollowers(false);
                sampleUser.setDescription("Description");
                sampleUser.setProfileBackgroundColor("setProfileBackgroundColor");
                sampleUser.setProfileBackgroundImageUrl("profileBackgroundImageUrl");
                sampleUser.setProfileBackgroundImageUrlHttps("profileBackgroundImageUrlHttps");
                sampleUser.setProfileBackgroundTiled(Boolean.FALSE);
                sampleUser.setProfileBannerImageUrl("setProfileBannerImageUrl");
                sampleUser.setProfileImageUrl("profileImageUrl");
                sampleUser.setProfileImageUrlHttps("profileImageUrlHttps");
                sampleUser.setProfileLinkColor("profileLinkColor");
                sampleUser.setProfileSidebarBorderColor("profileSidebarBorderColor");
                sampleUser.setProfileSidebarFillColor("profileSidebarFillColor");
                sampleUser.setProfileTextColor("profileTextColor");
                sampleUser.setProfileUseBackgroundImage(Boolean.FALSE);
                
		twitterUserRepository.save(sampleUser);
		
		classificationRepository.save(Arrays.asList(
				createClassification(1L, "Denuncia", true),
				createClassification(2L, "Observação", true),
				createClassification(3L, "Piada", false)
		));
	}

	@Test
	public void testTwitterUser() {
                TwitterUser sampleUser = twitterUserRepository.findByScreenname("marcioaguiar");
		assertEquals(new Integer(5), sampleUser.getFavouritesCount());
                
                assertEquals("Description", sampleUser.getDescription());
                assertEquals("setProfileBackgroundColor", sampleUser.getProfileBackgroundColor());
                assertEquals("profileBackgroundImageUrl", sampleUser.getProfileBackgroundImageUrl());
                assertEquals("profileBackgroundImageUrlHttps", sampleUser.getProfileBackgroundImageUrlHttps());
                assertEquals(Boolean.FALSE, sampleUser.getProfileBackgroundTiled());
                assertEquals("setProfileBannerImageUrl", sampleUser.getProfileBannerImageUrl());
                assertEquals("profileImageUrl", sampleUser.getProfileImageUrl());
                assertEquals("profileImageUrlHttps", sampleUser.getProfileImageUrlHttps());
                assertEquals("profileLinkColor", sampleUser.getProfileLinkColor());
                assertEquals("profileSidebarBorderColor", sampleUser.getProfileSidebarBorderColor());
                assertEquals("profileSidebarFillColor", sampleUser.getProfileSidebarFillColor());
                assertEquals("profileTextColor", sampleUser.getProfileTextColor());
                assertEquals(Boolean.FALSE, sampleUser.getProfileUseBackgroundImage());
                
		List<TwitterUser> users = twitterUserRepository.findAllOrderedById();
		assertEquals(4, users.size());
		assertEquals(new Long(4), users.get(users.size() - 1).getId());
		assertEquals(new Long(1), users.get(0).getId());
		assertEquals(3, twitterUserRepository.findAllWithOutdatedFollowers().size());
		twitterUserRepository.deleteByScreenname("diegocedrim");
		assertNull(twitterUserRepository.findByScreenname("diegocedrim"));
		assertEquals(0, twitterUserRepository.findAllRelevant().size());
		assertEquals(2, twitterUserRepository.findAllWithOutdatedFollowers().size());
	}
	
	@Test
	public void testClassificationRepository() {
		assertEquals(2, classificationRepository.findAllUsedInTwitterRank().size());
		assertEquals(3, classificationRepository.findAll().size());
		assertEquals("Denuncia", classificationRepository.findByKey("denun").getLabel());
	}
	
	@Test
	public void testFindAllGeotagged() {
		
		List<TweetClassification> classifications = classificationRepository.findAll();
		List<TwitterUser> users = twitterUserRepository.findAll();
		
		Tweet tweet = new Tweet();
		tweet.setId(1L);
		tweet.setClassification(classifications.get(0));
		tweet.setCreatedAt(Date.valueOf(LocalDate.of(2018, 5, 5)));
		tweet.setFavoriteCount(5);
		tweet.setGeolocation(new GeoLocation(-9, -10));
		tweet.setRetweetCount(10);
		tweet.setUser(users.get(0));
		
		tweetRepository.save(tweet);
	}

	@Test
	public void testFindAllUnclassifiedByLanguage() {
	}

	@Test
	public void testFindAllByLanguage() {
	}

	@Test
	public void testFindAllAfter() {
	}

	@Test
	public void testFindAllZika() {
	}

	@Test
	public void testFindAllGeotaggedPlain() {
	}
	
	
	private TwitterUser createUser(Long id, String name, String screenname, int favorites) {
		TwitterUser result = new TwitterUser(id);
		result.setFavouritesCount(favorites);
		result.setIsGeoEnabled(true);
		result.setName(name);
		result.setScreenName(screenname);
		return result;
	}
	
	private TweetClassification createClassification(Long id, String name, boolean ranked) {
		TweetClassification classification = new TweetClassification(id);
		classification.setDescription(name + " - " + name);
		classification.setKey(name.toLowerCase().substring(0, 5));
		classification.setLabel(name);
		classification.setUsedInTwitterRank(ranked);
		return classification;
	}
}
