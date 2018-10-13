package br.les.opus.gamification.services;
import br.les.opus.auth.core.domain.User;
import br.les.opus.gamification.domain.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.les.opus.gamification.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;


    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public Notification updateUserNotification(Notification notification) {

        try{
            return notificationRepository.save(notification);
        }catch (Exception e) {
            logger.error("Exception occur while update a Notification ",e);
            return null;
        }
    }


    public Notification findNotificationById(Long id) {

        try{
            return notificationRepository.findOne(id);
        }catch (Exception e) {
            logger.error("Exception occur while update a Notification ",e);
            return null;
        }
    }

    public Notification findByUser(User user){

        try{
            return notificationRepository.findNotificationByUser(user);
        }catch (Exception e) {
            logger.error("Exception occur while fetch Notification by User ",e);
            return null;
        }
    }


    public List<Notification> findByUser(User user, Integer limit){

        try{
            return notificationRepository.listNotificationsByUserWithLimit(user, new PageRequest(0, limit));
        }catch (Exception e) {
            logger.error("Exception occur while fetch Notification by User ", e);
            return null;
        }
    }


    public Notification save(Notification notification){

        try{
            return notificationRepository.save(notification);
        }catch (Exception e) {
            logger.error("Exception occur while save Notification ",e);
            return null;
        }
    }



    public Notification createNotificationObject(String message, User user){
        return new Notification(message, new Date(), user);
    }


    public Notification findByUserAndNotificationId(User user, Notification notification){

        try{
            return notificationRepository.findNotificationByUserAndNotificationId(user, notification);
        }catch (Exception e) {
            logger.error("Exception occur while fetch Notification by User and Notification Id ",e);
            return null;
        }
    }

}
