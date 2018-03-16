package br.les.opus.gamification.repositories;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.constraints.QuantityConstraint;

@Repository
public class QuantityConstraintRepository extends HibernateAbstractRepository<QuantityConstraint, Long>{

}
