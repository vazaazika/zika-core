package br.les.opus.instagram.repository;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.instagram.domain.InstagramUser;

@Repository
public class InstagramUserRepository extends HibernateAbstractRepository<InstagramUser, Long>{

}
