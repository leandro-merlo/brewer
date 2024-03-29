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

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.model.Usuario;
import br.com.manzatech.brewer.repositories.filter.Filter;
import br.com.manzatech.brewer.repositories.filter.UsuarioFilter;

public class UsuariosImpl extends AbstractImpl<Usuario> implements UsuariosQueries {

	@SuppressWarnings("unchecked")
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
		UsuarioFilter usuarioFilter = (UsuarioFilter) filter;
		List<Predicate> restrictions = new ArrayList<Predicate>();
		
		if (null != usuarioFilter) {
			if (StringUtils.hasText(usuarioFilter.getNome())) {
				restrictions.add(cb.like(cb.lower(root.get("nome")), "%" + usuarioFilter.getNome().toLowerCase() +"%"));
			}			
			if (StringUtils.hasText(usuarioFilter.getEmail())) {
				restrictions.add(cb.equal(cb.lower(root.get("email")), usuarioFilter.getNome().toLowerCase()));
			}
			if (!ObjectUtils.isEmpty(usuarioFilter.getGrupos())) {
				usuarioFilter.getGrupos().stream().forEach(g -> {
					restrictions.add(cb.isMember(g, root.get("grupos")));
				});
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
		Root<Usuario> root = query.from(Usuario.class);
		query.where(this.addRestrictions(filter, cb, root));
		query = query.select(cb.count(root).alias("count"));
		Query q = manager.createQuery(query);
		return (Long) q.getSingleResult();		
	}

	@Override
	protected void addFetches(Root<Usuario> root) {
		root.fetch("grupos", JoinType.INNER);		
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return 
			manager.createQuery("select distinct p.nome from Usuario u inner join u.grupos g "
					+ "inner join g.permissoes p where u = :usuario", String.class).
			setParameter("usuario", usuario).
			getResultList();
	}

}
