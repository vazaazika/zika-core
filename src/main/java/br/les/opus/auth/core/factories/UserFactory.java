package br.les.opus.auth.core.factories;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import br.les.opus.auth.core.domain.User;

@Component
public class UserFactory {

	public User createFromFacebook(facebook4j.User fbUser) {
		User user = new User();
		user.setEnabled(true);
		user.setLocked(false);
		user.setName(fbUser.getName());
		user.setUsername(fbUser.getEmail());
		String randomPassword = DigestUtils.md5Hex(UUID.randomUUID().toString()); //generates a random password
		user.setPassword(randomPassword);
		return user;
	}
}
