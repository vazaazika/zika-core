package br.les.opus.gamification.repositories;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.UserAction;

@Repository
public class UserActionRepository extends HibernateAbstractRepository<UserAction, Long> {

}
