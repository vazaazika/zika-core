package br.les.opus.twitter.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.twitter.domain.HashTag;

@Repository
@SuppressWarnings("unchecked")
public class HashTagRepository extends HibernateAbstractRepository<HashTag, Long> {
	
	public List<HashTag> findAllActive() {
		String hql = "from HashTag where active = true";
		return getSession().createQuery(hql).list();
	}
}
