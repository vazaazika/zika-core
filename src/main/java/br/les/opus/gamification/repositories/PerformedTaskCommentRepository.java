package br.les.opus.gamification.repositories;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.dengue.core.domain.PerformedTaskComment;

@Repository
public class PerformedTaskCommentRepository extends HibernateAbstractRepository<PerformedTaskComment, Long> {

}
