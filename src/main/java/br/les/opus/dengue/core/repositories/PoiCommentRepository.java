package br.les.opus.dengue.core.repositories;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.dengue.core.domain.PoiComment;

@Repository
public class PoiCommentRepository extends HibernateAbstractRepository<PoiComment, Long>{

}
