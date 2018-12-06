package br.les.opus.gamification.repositories;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.HealthAgent;

import org.hibernate.Query;

import org.springframework.stereotype.Repository;

@Repository
public class HealthAgentRepository extends HibernateAbstractRepository<HealthAgent, Long> {


    public HealthAgent findById (Long id) {

        String hql = "from HealthAgent where user_id = :user_id";
        Query query = getSession().createQuery(hql);
        query.setParameter("user_id",  id);
        Object obj = query.uniqueResult();
        if (obj != null) {
            return (HealthAgent)obj;
        }
        return null;

    }

}