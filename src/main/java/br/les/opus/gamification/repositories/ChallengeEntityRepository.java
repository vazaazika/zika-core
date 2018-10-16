package br.les.opus.gamification.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.challenge.ChallengeEntity;

@Repository
public class ChallengeEntityRepository extends HibernateAbstractRepository<ChallengeEntity, Long>{
	
	public String getEntityType(Long idEntity) {
		String hql = "select ce.type from ChallengeEntity ce where idEntity = :entityId";
		Query query = getSession().createQuery(hql);
		query.setParameter("entityId", idEntity);
		Object obj = query.uniqueResult();
		if (obj == null) {
			return null;
		}
		return (String) obj;
	}
	
	@SuppressWarnings("unchecked")
	public List<ChallengeEntity> getEntitiesByPlayer(Long idEntity) {
		String hql = "from ChallengeEntity where idEntity = :entityId and type = :typeEntity";
		Query query = getSession().createQuery(hql);

		query.setParameter("entityId", idEntity);
		query.setParameter("typeEntity", ChallengeEntity.PLAYERTYPE);
		
		return query.list();
	}

}
