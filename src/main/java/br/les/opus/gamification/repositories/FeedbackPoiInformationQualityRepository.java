package br.les.opus.gamification.repositories;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.feedback.FeedbackPoiInformationQuality;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackPoiInformationQualityRepository extends HibernateAbstractRepository<FeedbackPoiInformationQuality, Long> {
}