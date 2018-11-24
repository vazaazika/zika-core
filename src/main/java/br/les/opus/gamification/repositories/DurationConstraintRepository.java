package br.les.opus.gamification.repositories;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.constraints.DurationConstraint;

@Repository
public class DurationConstraintRepository extends HibernateAbstractRepository<DurationConstraint, Long>{

}
