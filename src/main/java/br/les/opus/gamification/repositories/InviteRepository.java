package br.les.opus.gamification.repositories;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Invite;
import br.les.opus.gamification.domain.Membership;
import org.springframework.stereotype.Repository;

/**
 * Created by andersonjso on 5/15/18.
 */
@Repository
public class InviteRepository extends HibernateAbstractRepository<Invite, Long> {
}
