package br.les.opus.gamification.repositories;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.ResetPassword;
import org.springframework.stereotype.Repository;

/**
 * Created by andersonjso on 5/16/18.
 */
@Repository
public class ResetPasswordRepository extends HibernateAbstractRepository<ResetPassword, Long> {
}


/*
@Repository
public class InviteRepository extends HibernateAbstractRepository<Invite, Long> {
}

 */