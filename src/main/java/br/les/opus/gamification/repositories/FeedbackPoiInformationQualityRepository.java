package br.les.opus.gamification.repositories;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.ResetPassword;
import br.les.opus.gamification.domain.feedback.FeedbackPoiInformationQuality;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackPoiInformationQualityRepository extends HibernateAbstractRepository<FeedbackPoiInformationQuality, Long> {

    public FeedbackPoiInformationQuality findByPoiAndStatus (Long idPoi, boolean status){
        String hql = " from FeedbackPoiInformationQuality where object_id = :id and resolved = :res";
        Query query = getSession().createQuery(hql);
        query.setParameter("id", idPoi);
        query.setParameter("res", status);

        Object result = query.uniqueResult();
        if (result == null) {
            return null;
        }
        return (FeedbackPoiInformationQuality)result;
    }
}
