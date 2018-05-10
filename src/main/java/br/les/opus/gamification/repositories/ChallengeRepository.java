package br.les.opus.gamification.repositories;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Challenge;

@Repository
public class ChallengeRepository extends HibernateAbstractRepository<Challenge, Long>{

}
