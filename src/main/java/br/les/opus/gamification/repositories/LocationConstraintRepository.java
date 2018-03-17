package br.les.opus.gamification.repositories;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.constraints.LocationConstraint;

@Repository
public class LocationConstraintRepository extends HibernateAbstractRepository<LocationConstraint, Long>{

}
