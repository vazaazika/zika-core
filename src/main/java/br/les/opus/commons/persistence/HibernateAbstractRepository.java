package br.les.opus.commons.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.les.opus.commons.persistence.builders.CriteriaBuilder;
import br.les.opus.commons.persistence.filtering.Filter;

@SuppressWarnings("unchecked")
public class HibernateAbstractRepository<T, Pk extends Serializable> implements PagingSortingFilteringRepository<T,Pk> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected List<T> getUniqueObjects(List<T> objects) {
		Set<T> set = new LinkedHashSet<>(objects);
		List<T> uniques = new ArrayList<>();
		uniques.addAll(set);
		return uniques;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected Long count(Criteria criteria) {
		return (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	protected Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public <S extends T> S save(S entity) {
		entity = (S)getSession().merge(entity);
		getSession().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		for (S s : entities) {
			this.save(s);
		}
		return entities;
	}

	@Override
	public T findOne(Pk id) {
		return (T)getSession().get(getEntityClass(), id);
	}

	@Override
	public boolean exists(Pk id) {
		return this.findOne(id) != null;
	}

	@Override
	public List<T> findAll() {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<T> list = criteria.list();
		return getUniqueObjects(list);
	}

	@Override
	public Iterable<T> findAll(Iterable<Pk> ids) {
		List<Pk> idsList = new ArrayList<>();
		for (Pk pk : ids) {
			idsList.add(pk);
		}
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.in("id", idsList));
		return getUniqueObjects(criteria.list());
	}

	@Override
	public long count() {
		return (Long) getSession().createCriteria(getEntityClass()).setProjection(Projections.rowCount()).uniqueResult();
	}
	
	@Override
	public void delete(Pk id) {
		T entity = this.findOne(id);
		this.delete(entity);
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		for (T t : entities) {
			this.delete(t);
		}
	}

	@Override
	public void deleteAll() {
		String name = getEntityClass().getSimpleName();
		String hql = "delete from " + name;
		getSession().createQuery(hql).executeUpdate();
	}

	@Override
	public Iterable<T> findAll(Sort sort) {
		CriteriaBuilder builder = new CriteriaBuilder(getSession(), getEntityClass());
		builder.addSort(sort);
		
		Criteria criteria = builder.getBuiltCriteria();
		return getUniqueObjects(criteria.list());
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		CriteriaBuilder builder = new CriteriaBuilder(getSession(), getEntityClass());
		if (pageable.getSort() != null) {
			builder.addSort(pageable.getSort());
		}
		
		Criteria criteria = builder.getBuiltCriteria();
		criteria.setFirstResult(pageable.getOffset());
		criteria.setMaxResults(pageable.getPageSize());
		
		List<T> content = getUniqueObjects(criteria.list());

		PageImpl<T> page = new PageImpl<>(content, pageable, this.count());
		return page;
	}

	@Override
	public Page<T> findAll(Pageable pageable, Filter filter) {
		CriteriaBuilder builder = new CriteriaBuilder(getSession(), getEntityClass());
		CriteriaBuilder countBuilder = new CriteriaBuilder(getSession(), getEntityClass());
		
		if (pageable.getSort() != null) {
			builder.addSort(pageable.getSort());
		}
		
		if (filter != null) {
			builder.addFilter(filter);
			countBuilder.addFilter(filter);
		}
		
		Criteria criteria = builder.getBuiltCriteria();
		criteria.setFirstResult(pageable.getOffset());
		criteria.setMaxResults(pageable.getPageSize());
		
		Criteria countCriteria = countBuilder.getBuiltCriteria();
		
		List<T> content = getUniqueObjects(criteria.list());
		Long count = this.count(countCriteria);
		PageImpl<T> page = new PageImpl<>(content, pageable, count);
		return page;
	}
	
	public List<T> findAllPlain(Pageable pageable, Filter filter) {
		CriteriaBuilder builder = new CriteriaBuilder(getSession(), getEntityClass());
		if (pageable.getSort() != null) {
			builder.addSort(pageable.getSort());
		}
		if (filter != null) {
			builder.addFilter(filter);
		}
		Criteria criteria = builder.getBuiltCriteria();
		criteria.setFirstResult(pageable.getOffset());
		criteria.setMaxResults(pageable.getPageSize());
		List<T> content = getUniqueObjects(criteria.list());
		return content;
	}
	
	public List<T> findAllPlain(Pageable pageable) {
		return this.findAllPlain(pageable, null);
	}
	
	public void flush () {
		this.getSession().flush();
	}
	
	public void evict (T t) {
		this.getSession().evict(t);
	}
}
