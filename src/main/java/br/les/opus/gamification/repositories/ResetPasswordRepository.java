package br.les.opus.gamification.repositories;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.ResetPassword;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by andersonjso on 5/16/18.
 */
@Repository
public class ResetPasswordRepository extends HibernateAbstractRepository<ResetPassword, Long> {

    public ResetPassword findToken (String token){
        Criteria criteria = this.getSession().createCriteria(this.getEntityClass());
        criteria.add(Restrictions.eq("hashedToken", token).ignoreCase());
        Object obj = criteria.uniqueResult();
        return obj == null?null:(ResetPassword) obj;
    }
}
