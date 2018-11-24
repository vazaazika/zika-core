package br.les.opus.gamification.repositories;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Invite;
import br.les.opus.gamification.domain.Player;

/**
 * 
 * Created by andersonjso on 5/15/18.
 */
@Repository
public class InviteRepository extends HibernateAbstractRepository<Invite, Long> {

    public Player findUserByInviteId(String inviteToken){
        String hql = "SELECT i.user FROM Invite i " +
                "LEFT JOIN i.user as u " +
                "WHERE i.hashedToken = :inviteToken";

        Query query = getSession().createQuery(hql);
        query.setParameter("inviteToken", inviteToken);
        Object obj = query.uniqueResult();

        if (obj == null) {
            return null;
        }

        return (Player)obj;
    }
}
