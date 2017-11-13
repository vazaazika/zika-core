package br.les.opus.dengue.core.repositories;


import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.dengue.core.fields.FieldOption;
import br.les.opus.dengue.core.fields.FieldValue;

@Repository
public class FieldValueRepository extends HibernateAbstractRepository<FieldValue, Long>{

	public FieldOption findSelectedOption(FieldValue fieldValue) {
		String hql = "from FieldOption where field.id = :idField and value = :value";
		Query query = getSession().createQuery(hql);
		query.setParameter("idField", fieldValue.getField().getId());
		query.setParameter("value", fieldValue.getValue());
		query.setCacheable(true);
		query.setCacheRegion("i18n.findSelectedOption");
		List<?> list = query.list();
		if (list.isEmpty()) {
			return null;
		}
		return (FieldOption)list.get(0);
	}
}
