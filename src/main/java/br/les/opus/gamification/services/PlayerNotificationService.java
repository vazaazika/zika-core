package br.les.opus.gamification.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import br.les.opus.gamification.domain.Badge;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.notification.Notification;
import br.les.opus.gamification.domain.notification.Notification.Type;
import br.les.opus.gamification.domain.notification.PlayerNotification;
import br.les.opus.gamification.repositories.BadgeRepository;
import br.les.opus.gamification.repositories.PlayerNotificationRepository;

@Service
@Transactional
public class PlayerNotificationService {
	@Autowired
	private PlayerNotificationRepository repository;

	
	public void verifyPlayerNotificationCreation(Player oldPlayer, Player newPlayer) {
		
		//check xp
		if(oldPlayer.getXp() == newPlayer.getXp()) {
			return;
		}
		
		//verify if player changed level
		if(oldPlayer.getLevel() != newPlayer.getLevel()) {
			Notification notification = new Notification();
			notification.setTitle(Notification.Type.LEVELUP.toString());
			
			StringBuffer buffer = new StringBuffer();
			
			buffer.append("Parabéns " + newPlayer.getName() + "!\n");
			buffer.append("Você acaba de subir para o nível " + newPlayer.getLevel());
			
			notification.setDescription(buffer.toString());
			
			PlayerNotification pn = new PlayerNotification(newPlayer, Type.LEVELUP, notification);
			
			repository.save(pn);
			
			return;
		}
		
	}

}
