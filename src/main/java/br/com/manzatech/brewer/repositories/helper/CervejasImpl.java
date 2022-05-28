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

import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.model.Cerveja;
import br.com.manzatech.brewer.repositories.filter.CervejaFilter;

public class CervejasImpl implements CervejasQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Cerveja> filtrar(CervejaFilter cervejaFilter) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Cerveja> query = cb.createQuery(Cerveja.class);
		Root<Cerveja> root = query.from(Cerveja.class);
		root.fetch("estilo", JoinType.INNER);
		query = query.select(root);
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
		
		
		if (!restrictions.isEmpty()) {
			Predicate[] array = new Predicate[restrictions.size()];
			restrictions.toArray(array);
			query = query.where(array);			
		}
		
		Query q = manager.createQuery(query);
		return q.getResultList();
	}

}
