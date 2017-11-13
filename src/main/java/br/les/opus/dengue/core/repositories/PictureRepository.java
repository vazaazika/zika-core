package br.les.opus.dengue.core.repositories;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.dengue.core.domain.Picture;

@Repository
public class PictureRepository extends HibernateAbstractRepository<Picture, Long> {

}
