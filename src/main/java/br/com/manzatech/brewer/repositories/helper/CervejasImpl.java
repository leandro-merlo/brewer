package br.com.manzatech.brewer.repositories.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.model.Cerveja;
import br.com.manzatech.brewer.repositories.filter.CervejaFilter;

public class CervejasImpl implements CervejasQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	public Page<Cerveja> filtrar(CervejaFilter cervejaFilter, Pageable pageable) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Cerveja> query = cb.createQuery(Cerveja.class);
		Root<Cerveja> root = query.from(Cerveja.class);
		root.fetch("estilo", JoinType.INNER);
		query = query.select(root);
		query.where(addRestrictions(cervejaFilter, cb, root));
		
		
		Query q = manager.createQuery(query);
		
		int size = pageable.getPageSize();
		int page = pageable.getPageNumber();
		
		q.setFirstResult(page * size);
		q.setMaxResults(size);
		
		List<Cerveja> cervejas = q.getResultList();
		Page<Cerveja> result = new PageImpl<Cerveja>(cervejas, pageable, this.rowCount(cervejaFilter));
		
		return result;
	}

	private Predicate[] addRestrictions(CervejaFilter cervejaFilter, CriteriaBuilder cb, Root<Cerveja> root) {
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
	
	private Long rowCount(CervejaFilter cervejaFilter) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<Cerveja> root = query.from(Cerveja.class);
		query.where(this.addRestrictions(cervejaFilter, cb, root));
		query = query.select(cb.count(root).alias("count"));
		Query q = manager.createQuery(query);
		return (Long) q.getSingleResult();		
	}

}
