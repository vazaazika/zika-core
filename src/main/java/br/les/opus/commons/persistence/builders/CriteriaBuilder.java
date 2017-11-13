package br.les.opus.commons.persistence.builders;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Sort;

import br.les.opus.commons.persistence.filtering.Clause;
import br.les.opus.commons.persistence.filtering.Filter;

public class CriteriaBuilder {

private Criteria criteria;
	
	public CriteriaBuilder(Session session, Class<?> clazz) {
		this.criteria = session.createCriteria(clazz);
	}
	
	public void addCriterion(Criterion criterion) {
		this.criteria.add(criterion);
	}

	public void addFilter(Filter filter) {
		for (Clause clause: filter) {
			clause.createAliases(criteria);
			criteria.add(clause.toCrierion());
		}
	}

	public void addSort(Sort sort) {
		for (Sort.Order order : sort) {
			if (order.isAscending()) {
				criteria.addOrder(Order.asc(order.getProperty()));
			} else {
				criteria.addOrder(Order.desc(order.getProperty()));
			}
		}
	}

	public Criteria getBuiltCriteria() {
		return criteria;
	}

}
