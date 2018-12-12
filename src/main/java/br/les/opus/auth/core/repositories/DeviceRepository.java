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
        Criteria criteria = getSession().createCriteria(getEntityClass());
        criteria.createAlias("user", "user");
        criteria.add(Restrictions.eq("user.id", user.getId()));
        criteria.add(Restrictions.eq("token", token));
        Object obj = criteria.uniqueResult();
        return (obj == null)? null : (Device) obj;
    }
}
