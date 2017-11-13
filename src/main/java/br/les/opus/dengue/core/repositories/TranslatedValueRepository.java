package br.les.opus.dengue.core.repositories;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.dengue.core.i18n.I18nMetaField;
import br.les.opus.dengue.core.i18n.TranslatedValue;

@Repository
public class TranslatedValueRepository extends HibernateAbstractRepository<TranslatedValue, Long>{

	public TranslatedValue findOne(String classFqn, String fieldName, String language, String objectId) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq("classFqn", classFqn));
		criteria.add(Restrictions.eq("fieldName", fieldName));
		criteria.add(Restrictions.eq("language", language));
		criteria.add(Restrictions.eq("objectId", objectId));
		criteria.setCacheable(true);
		criteria.setCacheRegion("i18n.TranslatedValues");
		Object obj = criteria.uniqueResult();
		if (obj == null) {
			return null;
		}
		return (TranslatedValue)obj;
	}
	
	public TranslatedValue findOne(I18nMetaField metaField, String language) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq("classFqn", metaField.getClassFqn()));
		criteria.add(Restrictions.eq("fieldName", metaField.getFieldName()));
		criteria.add(Restrictions.eq("language", language));
		criteria.add(Restrictions.eq("objectId", metaField.getObjectId()));
		criteria.setCacheable(true);
		criteria.setCacheRegion("i18n.TranslatedValues");
		Object obj = criteria.uniqueResult();
		if (obj == null) {
			return null;
		}
		return (TranslatedValue)obj;
	}
}
