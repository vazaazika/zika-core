package br.les.opus.instagram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.les.opus.instagram.domain.InstagramUser;
import br.les.opus.instagram.repository.InstagramUserRepository;

@Service
public class InstagramUserService {

	@Autowired
	private InstagramUserRepository userRepository;
	
	public InstagramUser saveOrRetrieve(InstagramUser user) {
		InstagramUser retrievedUser = userRepository.findOne(user.getId());
		if (retrievedUser != null) {
			return retrievedUser;
		}
		return userRepository.save(user);
	}
}
