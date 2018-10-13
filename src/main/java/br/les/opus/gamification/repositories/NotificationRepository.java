package br.les.opus.gamification.repositories;


import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.*;
import org.hibernate.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationRepository extends HibernateAbstractRepository<Notification, Long> {


public Notification findNotificationByUser(User user){

    StringBuilder builder = new StringBuilder();
    builder.append("from Notification n ");
    builder.append("where ");
    builder.append("n.user.id =: userId");

    Query query = getSession().createQuery(builder.toString());
    query.setParameter("userId", user.getId());
    Object obj = query.uniqueResult();
    if (obj == null) {
        return null;
    }
    return (Notification) obj;
}


public  List<Notification> listNotificationsByUserWithLimit(User user, Pageable pageable){

        StringBuilder builder = new StringBuilder();
        builder.append("from Notification n ");
        builder.append("where ");
        builder.append("n.user.id =: userId");
        builder.append("ORDER BY");
        builder.append("n.createdAt DESC");

        Query query = getSession().createQuery(builder.toString());
        query.setParameter("userId", user.getId());
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Notification> notifications = query.list();

        if (notifications == null) {
            return null;
        }
        return notifications;

    }

    public Notification findNotificationByUserAndNotificationId(User user, Notification notification){

        StringBuilder builder = new StringBuilder();
        builder.append("from Notification n ");
        builder.append("where ");
        builder.append(" n.notification.id = :notificationId ");
        builder.append("  and n.user.id = :userId ");

        Query query = getSession().createQuery(builder.toString());
        query.setParameter("userId", user.getId());
        query.setParameter("notificationId", notification.getId());
        Object obj = query.uniqueResult();
        if (obj == null) {
            return null;
        }
        return (Notification) obj;
    }




}
