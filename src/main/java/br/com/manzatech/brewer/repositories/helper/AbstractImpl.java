package br.com.manzatech.brewer.repositories.helper;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.com.manzatech.brewer.repositories.filter.Filter;

public abstract class AbstractImpl<T> implements Queries<T>{

	@PersistenceContext
	protected EntityManager manager;
	
	@Override
	public Page<T> filtrar(Filter filter, Pageable pageable) {
		@SuppressWarnings("unchecked")
		Class<T> typeOfT = (Class<T>)
                ((ParameterizedType)getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];		
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(typeOfT);
		Root<T> root = query.from(typeOfT);
		addFetches(root);
		query = query.select(root);
		query.where(addRestrictions(filter, cb, root));
		
		
		Sort sort = pageable.getSort();
		if (null != sort && sort.iterator().hasNext()) {
			Sort.Order order = sort.iterator().next();
			String field = order.getProperty();
			query.orderBy(order.isDescending() ? cb.desc(root.get(field)) : cb.asc(root.get(field)));
		}
		
		Query q = manager.createQuery(query);
		
		int size = pageable.getPageSize();
		int page = pageable.getPageNumber();
		
		q.setFirstResult(page * size);
		q.setMaxResults(size);		
		
		@SuppressWarnings("unchecked")
		List<T> resultset = q.getResultList();
		Page<T> result = new PageImpl<T>(resultset, pageable, this.rowCount(filter));
		
		return result;
	}

	protected abstract Predicate[] addRestrictions(Filter filter, CriteriaBuilder cb, Root<T> root);
	
	protected abstract Long rowCount(Filter filter);

	protected abstract void addFetches(Root<T> root);

}
