package br.les.opus.gamification.repositories;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.feedback.FeedbackChangePoiStatus;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackChangePoiStatusRepository extends HibernateAbstractRepository<FeedbackChangePoiStatus, Long> {
}