package br.les.opus.auth.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import br.les.opus.auth.core.domain.User;
import br.les.opus.auth.core.factories.UserFactory;
import br.les.opus.auth.core.repositories.UserRepository;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;

@Service
@PropertySource("classpath:auth.properties")
public class FacebookService {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	private Facebook getFacebookAPI(String accessToken) {
		String appId = env.getProperty("facebook.appId");
		String appSecret = env.getProperty("facebook.appSecret");
		
		Facebook facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(appId, appSecret);
		facebook.setOAuthAccessToken(new AccessToken(accessToken));
		
		return facebook;
	}
	
	/**
	 * Loads an user from local database using an Facebook access token. If no local 
	 * user exists with the same facebook user email, we create a new one.
	 * 
	 * @param accessToken
	 * @return
	 * @throws FacebookException
	 */
	public User loadOrCreateUser(String accessToken) throws FacebookException {
		Facebook fb = this.getFacebookAPI(accessToken);
		
		facebook4j.User fbUser = fb.getMe();
		User user = new UserFactory().createFromFacebook(fbUser);
		
		User existingUser = userRepository.findByUsername(user.getUsername());
		if (existingUser == null) {
			existingUser =  userService.save(user);
		} 
		return existingUser;
	}
}
