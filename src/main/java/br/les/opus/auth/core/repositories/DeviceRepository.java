package br.les.opus.auth.core.repositories;


import br.les.opus.auth.core.domain.Device;
import br.les.opus.auth.core.domain.Role;
import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.HibernateAbstractRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceRepository extends HibernateAbstractRepository<Device, Long> {


    public Device findDeviceByUserAndToken(User user, String token) {

        String hql = "from Device where user_id = :user_id and token = :token";
        Query query = getSession().createQuery(hql);
        query.setParameter("user_id",  user.getId());
        query.setParameter("token",  token);
        Object obj = query.uniqueResult();
        if (obj != null) {
            return (Device)obj;
        }
        return null;

    }




}
