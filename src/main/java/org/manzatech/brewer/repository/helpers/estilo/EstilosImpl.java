package org.manzatech.brewer.repository.helpers.estilo;

import org.manzatech.brewer.model.Estilo;
import org.manzatech.brewer.model.Estilo_;
import org.manzatech.brewer.repository.filters.EstiloFilter;
import org.manzatech.brewer.repository.helpers.estilo.EstilosQueries;
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

public class EstilosImpl implements EstilosQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PaginationUtil paginationUtil;

    @Override
    public Page<Estilo> filtrar(EstiloFilter estiloFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Estilo> criteria = builder.createQuery(Estilo.class);
        Root<Estilo> root = criteria.from(Estilo.class);
        criteria.where(filter(estiloFilter, builder, root));
        TypedQuery query = paginationUtil.preparar(criteria, pageable, builder, root, entityManager);
        PageImpl<Estilo> page = new PageImpl<>(query.getResultList(), pageable, count(estiloFilter));
        return page;
    }

    private Predicate filter(EstiloFilter estiloFilter, CriteriaBuilder builder, Path root){
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(estiloFilter.getNome())){
            Expression x = builder.lower(root.get(Estilo_.nome));
            Predicate p = builder.like(x, "%" + estiloFilter.getNome().toLowerCase() + "%");
            predicates.add(builder.and(p));
        }

        Predicate temp[] = new Predicate[predicates.size()];
        predicates.toArray(temp);
        Predicate and = builder.and(temp);
        return and;
    }

    private long count(EstiloFilter estiloFilter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<Estilo> root = criteria.from(Estilo.class);

        criteria.where(filter(estiloFilter, builder, root));
        criteria.select(builder.count(root.get(Estilo_.id)));

        TypedQuery<Long> query = entityManager.createQuery(criteria);
        return query.getSingleResult();
    }
}
