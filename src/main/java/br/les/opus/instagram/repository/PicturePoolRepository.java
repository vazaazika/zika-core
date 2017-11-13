package br.les.opus.instagram.repository;

import org.springframework.stereotype.Component;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.instagram.domain.PicturePool;

@Component
public class PicturePoolRepository extends HibernateAbstractRepository<PicturePool, Long>{

}
