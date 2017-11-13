package br.les.opus.dengue.core.repositories;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.dengue.core.domain.PoiType;

@Repository
public class PoiTypeRepository extends HibernateAbstractRepository<PoiType, Long>{

}
