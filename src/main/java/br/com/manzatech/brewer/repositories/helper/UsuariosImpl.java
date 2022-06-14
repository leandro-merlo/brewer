package br.com.manzatech.brewer.repositories.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.manzatech.brewer.model.Usuario;
import br.com.manzatech.brewer.repositories.filter.Filter;

public class UsuariosImpl extends AbstractImpl<Usuario> implements UsuariosQueries {

	@Override
	public Optional<Usuario> porEmailEAtivo(String email) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
		Root<Usuario> root = query.from(Usuario.class);
		
		root.fetch("grupos", JoinType.LEFT);
		
		query = query.select(root);
		List<Predicate> restrictions = new ArrayList<Predicate>();
		restrictions.add(cb.equal(cb.lower(root.get("email")), email.toLowerCase()));
		restrictions.add(cb.isTrue(root.get("ativo")));
		Predicate[] array = new Predicate[0];
		if (!restrictions.isEmpty()) {
			array = new Predicate[restrictions.size()];
			restrictions.toArray(array);
		}				
		query.where(array);
		Query q = manager.createQuery(query);
		
		return q.getResultList().stream().findFirst();
	}

	@Override
	protected Predicate[] addRestrictions(Filter filter, CriteriaBuilder cb, Root<Usuario> root) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Long rowCount(Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void addFetches(Root<Usuario> root) {
		// TODO Auto-generated method stub
		
	}

}
