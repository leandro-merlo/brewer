package br.com.manzatech.brewer.repositories.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.model.Estilo;
import br.com.manzatech.brewer.repositories.filter.EstiloFilter;
import br.com.manzatech.brewer.repositories.filter.Filter;

public class EstilosImpl extends AbstractImpl<Estilo> implements EstilosQueries {

	@Override
	protected Predicate[] addRestrictions(Filter filter, CriteriaBuilder cb, Root<Estilo> root) {
		EstiloFilter estiloFilter = (EstiloFilter) filter;
		List<Predicate> restrictions = new ArrayList<Predicate>();
		if (null != estiloFilter) {
			if (StringUtils.hasText(estiloFilter.getNome())) {
				restrictions.add(cb.like(cb.lower(root.get("nome")), 
						"%" + estiloFilter.getNome().toLowerCase() + "%"));
			}			
		}
		Predicate[] array = new Predicate[0];
		if (!restrictions.isEmpty()) {
			array = new Predicate[restrictions.size()];
			restrictions.toArray(array);
		}
		return array;
	}

	@Override
	protected Long rowCount(Filter filter) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<Estilo> root = query.from(Estilo.class);
		query.where(this.addRestrictions(filter, cb, root));
		query = query.select(cb.count(root).alias("count"));
		Query q = manager.createQuery(query);
		return (Long) q.getSingleResult();		
	}

	@Override
	protected void addFetches(Root<Estilo> root) {		
	}



}
