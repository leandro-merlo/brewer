package br.com.manzatech.brewer.repositories.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.model.Cliente;
import br.com.manzatech.brewer.repositories.filter.ClienteFilter;
import br.com.manzatech.brewer.repositories.filter.Filter;

public class ClientesImpl extends AbstractImpl<Cliente> implements ClientesQueries {

	@Override
	protected Predicate[] addRestrictions(Filter filter, CriteriaBuilder cb, Root<Cliente> root) {
		ClienteFilter clienteFilter = (ClienteFilter) filter;
		List<Predicate> restrictions = new ArrayList<Predicate>();
		if (null != clienteFilter) {
			if (StringUtils.hasText(clienteFilter.getNome())) {
				restrictions.add(cb.like(cb.lower(root.get("nome")), 
						"%" + clienteFilter.getNome().toLowerCase() + "%"));
			}
			if (StringUtils.hasText(clienteFilter.getDocumento())) {
				restrictions.add(cb.equal(root.get("documento"), clienteFilter.getDocumento()));
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
		Root<Cliente> root = query.from(Cliente.class);
		query.where(this.addRestrictions(filter, cb, root));
		query = query.select(cb.count(root).alias("count"));
		Query q = manager.createQuery(query);
		return (Long) q.getSingleResult();		
	}

	@Override
	protected void addFetches(Root<Cliente> root) {
		Fetch endereco = root.fetch("endereco", JoinType.LEFT);
		Fetch cidade = endereco.fetch("cidade", JoinType.LEFT);
		cidade.fetch("estado", JoinType.LEFT);		
	}

}
