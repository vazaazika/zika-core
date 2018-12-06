package br.les.opus.gamification.repositories;

import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.HealthAgent;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class HealthAgentRepository extends HibernateAbstractRepository<HealthAgent, Long> {



    public HealthAgent findById(Long id) {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        criteria.add(Restrictions.eq("user_id", id).ignoreCase());
        Object obj = criteria.uniqueResult();
        return (obj == null)? null : (HealthAgent)obj;
    }



}