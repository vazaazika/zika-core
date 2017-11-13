package br.les.opus.instagram.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.les.opus.instagram.domain.Comment;
import br.les.opus.instagram.domain.InstagramUser;
import br.les.opus.instagram.domain.Media;
import br.les.opus.instagram.domain.UserInPhoto;
import br.les.opus.instagram.repository.MediaRepository;

@Service
public class MediaService {
	
	@Autowired
	private InstagramUserService userService;
	
	@Autowired
	private MediaRepository mediaRepository;
	
	public boolean existis(Media media) {
		return mediaRepository.findOne(media.getId()) != null;
	}
	
	public void save(List<Media> medias) {
		for (Media media : medias) {
			if (mediaRepository.findOne(media.getId()) == null) {
				this.save(media);
			}
		}
	}
	
	public void save(Media media) {
		media.setUser(userService.saveOrRetrieve(media.getUser()));
		
		for (Comment comment : media.getComments().getComments()) {
			comment.setUser(userService.saveOrRetrieve(comment.getUser()));
		}
		
		Comment caption = media.getCaption();
		if (caption != null) {
			caption.setUser(userService.saveOrRetrieve(caption.getUser()));
		}
		
		List<InstagramUser> likers = media.getLikes().getLikers();
		for (int i = 0; i < likers.size(); i++) {
			likers.set(i, userService.saveOrRetrieve(likers.get(i)));
		}
		
		List<UserInPhoto> taggedUsers = media.getTaggedUsers();
		for (UserInPhoto userInPhoto : taggedUsers) {
			userInPhoto.setUser(userService.saveOrRetrieve(userInPhoto.getUser()));
			userInPhoto.setMedia(media);
		}
		
		mediaRepository.save(media);
	}
}
