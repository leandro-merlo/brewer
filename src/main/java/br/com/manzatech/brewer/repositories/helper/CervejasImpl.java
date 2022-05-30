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

import br.com.manzatech.brewer.model.Cerveja;
import br.com.manzatech.brewer.repositories.filter.CervejaFilter;
import br.com.manzatech.brewer.repositories.filter.Filter;

public class CervejasImpl extends AbstractImpl<Cerveja> implements CervejasQueries {

	protected Predicate[] addRestrictions(Filter filter, CriteriaBuilder cb, Root<Cerveja> root) {
		CervejaFilter cervejaFilter = (CervejaFilter) filter;
		List<Predicate> restrictions = new ArrayList<Predicate>();
		
		if (cervejaFilter != null) {
			if (StringUtils.hasText(cervejaFilter.getSku())) {
				restrictions.add(cb.equal(root.get("sku"), cervejaFilter.getSku()));
			}
			if (StringUtils.hasText(cervejaFilter.getNome())) {
				restrictions.add(cb.like(cb.lower(root.get("nome")), 
						"%" + cervejaFilter.getNome().toLowerCase() + "%"));
			}
			if (cervejaFilter.getEstilo() != null) {
				restrictions.add(cb.equal(root.get("estilo"), cervejaFilter.getEstilo()));
			}
			if (cervejaFilter.getSabor() != null) {
				restrictions.add(cb.equal(root.get("sabor"), cervejaFilter.getSabor()));
			}
			if (cervejaFilter.getOrigem() != null) {
				restrictions.add(cb.equal(root.get("origem"), cervejaFilter.getOrigem()));
			}
			if (cervejaFilter.isBetweenFilter()) {
				restrictions.add(cb.between(root.get("valor"), cervejaFilter.getPrecoDe(), cervejaFilter.getPrecoAte()));
			} else if (cervejaFilter.isGreaterFilter()) {
				restrictions.add(cb.ge(root.get("valor"), cervejaFilter.getPrecoDe()));				
			} else if (cervejaFilter.isLowerFilter()) {
				restrictions.add(cb.le(root.get("valor"), cervejaFilter.getPrecoAte()));								
			}
		}

		Predicate[] array = new Predicate[0];
		if (!restrictions.isEmpty()) {
			array = new Predicate[restrictions.size()];
			restrictions.toArray(array);
		}
		
		return array;
	}
	
	protected Long rowCount(Filter filter) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<Cerveja> root = query.from(Cerveja.class);
		query.where(this.addRestrictions(filter, cb, root));
		query = query.select(cb.count(root).alias("count"));
		Query q = manager.createQuery(query);
		return (Long) q.getSingleResult();		
	}

	@Override
	protected void addFetches(Root<Cerveja> root) {
		root.fetch("estilo", JoinType.INNER);		
	}


}
