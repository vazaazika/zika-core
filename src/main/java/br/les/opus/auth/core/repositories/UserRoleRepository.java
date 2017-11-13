package br.les.opus.auth.core.repositories;

import org.springframework.stereotype.Repository;

import br.les.opus.auth.core.domain.UserRole;
import br.les.opus.commons.persistence.HibernateAbstractRepository;

@Repository
public class UserRoleRepository extends HibernateAbstractRepository<UserRole, Long> {

}
