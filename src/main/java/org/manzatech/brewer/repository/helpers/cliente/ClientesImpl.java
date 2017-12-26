package org.manzatech.brewer.repository.helpers.cliente;

import org.manzatech.brewer.model.*;
import org.manzatech.brewer.repository.filters.ClienteFilter;
import org.manzatech.brewer.repository.pagination.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;

public class ClientesImpl implements ClientesQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PaginationUtil paginationUtil;

    @Override
    public Page<Cliente> filtrar(ClienteFilter cervejaFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteria = builder.createQuery(Cliente.class);
        Root<Cliente> root = criteria.from(Cliente.class);

        Join<Cliente, Endereco>  end = root.join(Cliente_.endereco);
        end.fetch(Endereco_.cidade, JoinType.LEFT).fetch(Cidade_.estado, JoinType.LEFT);

        criteria.where(filter(cervejaFilter, builder, root));
        TypedQuery query = paginationUtil.preparar(criteria, pageable, builder, root, entityManager);
        PageImpl<Cliente> page = new PageImpl<Cliente>(query.getResultList(), pageable, count(cervejaFilter));
        return page;
    }

    private Predicate filter(ClienteFilter clienteFilter, CriteriaBuilder builder, Path root){
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(clienteFilter.getNome())){
            Expression x = builder.lower(root.get(Cliente_.nome));
            Predicate p = builder.like(x, "%" + clienteFilter.getNome().toLowerCase() + "%");
            predicates.add(builder.and(p));
        }

        if (!StringUtils.isEmpty(clienteFilter.getDocumento())){
            Predicate p = builder.equal(root.get(Cliente_.cpfCnpj), clienteFilter.getDocumento());
            predicates.add(builder.and(p));
        }

        Predicate temp[] = new Predicate[predicates.size()];
        predicates.toArray(temp);
        Predicate and = builder.and(temp);
        return and;
    }

    private long count(ClienteFilter clienteFilter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<Cliente> root = criteria.from(Cliente.class);

        criteria.where(filter(clienteFilter, builder, root));
        criteria.select(builder.count(root.get(Cliente_.id)));

        TypedQuery<Long> query = entityManager.createQuery(criteria);
        return query.getSingleResult();
    }    
}
