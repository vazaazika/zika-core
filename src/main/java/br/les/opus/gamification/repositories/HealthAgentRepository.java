package br.les.opus.gamification.repositories;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.HealthAgent;

import org.springframework.stereotype.Repository;

@Repository
public class HealthAgentRepository extends HibernateAbstractRepository<HealthAgent, Long> {

}