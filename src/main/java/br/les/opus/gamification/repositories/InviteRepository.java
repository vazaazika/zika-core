package br.les.opus.gamification.repositories;

import br.les.opus.auth.core.domain.Resource;
import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Invite;
import br.les.opus.gamification.domain.Membership;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

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
