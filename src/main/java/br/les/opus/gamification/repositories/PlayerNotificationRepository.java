package br.les.opus.gamification.repositories;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.notification.PlayerNotification;

@Repository
public class PlayerNotificationRepository extends HibernateAbstractRepository<PlayerNotification, Long>{

}
