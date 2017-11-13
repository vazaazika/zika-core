package br.les.opus.instagram.repository;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.instagram.domain.Media;

@Repository
public class MediaRepository extends HibernateAbstractRepository<Media, String> {

}
