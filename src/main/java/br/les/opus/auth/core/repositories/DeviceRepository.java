package br.les.opus.auth.core.repositories;


import br.les.opus.auth.core.domain.Device;
import br.les.opus.commons.persistence.HibernateAbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceRepository extends HibernateAbstractRepository<Device, Long> {


}
