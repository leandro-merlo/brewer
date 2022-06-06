package br.com.manzatech.brewer.repositories.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.model.Cidade;
import br.com.manzatech.brewer.repositories.filter.CidadeFilter;
import br.com.manzatech.brewer.repositories.filter.Filter;

public class CidadesImpl extends AbstractImpl<Cidade> implements CidadesQueries {

	@Override
	protected Predicate[] addRestrictions(Filter filter, CriteriaBuilder cb, Root<Cidade> root) {
		CidadeFilter cidadeFilter = (CidadeFilter) filter;
		List<Predicate> restrictions = new ArrayList<Predicate>();
		if (null != cidadeFilter) {
			if (StringUtils.hasText(cidadeFilter.getNome())) {
				restrictions.add(cb.like(cb.lower(root.get("nome")), 
						"%" + cidadeFilter.getNome().toLowerCase() + "%"));
			}
			if (null != cidadeFilter.getEstado()) {
				restrictions.add(cb.equal(root.get("estado"), cidadeFilter.getEstado()));
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
		Root<Cidade> root = query.from(Cidade.class);
		query.where(this.addRestrictions(filter, cb, root));
		query = query.select(cb.count(root).alias("count"));
		Query q = manager.createQuery(query);
		return (Long) q.getSingleResult();		
	}

	@Override
	protected void addFetches(Root<Cidade> root) {
		root.fetch("estado", JoinType.INNER);		
	}

}
