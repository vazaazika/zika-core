package br.les.opus.gamification.repositories;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.feedback.Feedback;
import br.les.opus.gamification.domain.feedback.FeedbackType;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackTypeRepository extends HibernateAbstractRepository<FeedbackType, Long> {
}
