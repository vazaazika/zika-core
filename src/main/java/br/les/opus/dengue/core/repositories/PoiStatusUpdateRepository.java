package br.les.opus.dengue.core.repositories;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.dengue.core.domain.PoiStatusUpdate;

@Repository
public class PoiStatusUpdateRepository extends HibernateAbstractRepository<PoiStatusUpdate, Long>{

}
