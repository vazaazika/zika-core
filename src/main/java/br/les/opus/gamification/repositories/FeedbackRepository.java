package br.les.opus.gamification.repositories;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.ResetPassword;
import br.les.opus.gamification.domain.feedback.Feedback;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackRepository extends HibernateAbstractRepository<Feedback, Long> {
}
