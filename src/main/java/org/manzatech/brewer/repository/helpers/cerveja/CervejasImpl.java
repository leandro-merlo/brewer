package org.manzatech.brewer.repository.helpers.cerveja;

import org.manzatech.brewer.model.Cerveja;
import org.manzatech.brewer.model.Cerveja_;
import org.manzatech.brewer.repository.filters.CervejaFilter;
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

public class CervejasImpl implements CervejasQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PaginationUtil paginationUtil;

    @Override
    public Page<Cerveja> filtrar(CervejaFilter cervejaFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cerveja> criteria = builder.createQuery(Cerveja.class);
        Root<Cerveja> root = criteria.from(Cerveja.class);
        root.fetch(Cerveja_.estilo);
        criteria.where(filter(cervejaFilter, builder, root));
        TypedQuery query = paginationUtil.preparar(criteria, pageable, builder, root, entityManager);
        PageImpl<Cerveja> page = new PageImpl<Cerveja>(query.getResultList(), pageable, count(cervejaFilter));
        return page;
    }

    private Predicate filter(CervejaFilter cervejaFilter, CriteriaBuilder builder, Path root){
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(cervejaFilter.getSku())){
            Expression x = builder.lower(root.get(Cerveja_.sku));
            Predicate p = builder.equal(x, cervejaFilter.getSku().toLowerCase());
            predicates.add(builder.and(p));
        }

        if (!StringUtils.isEmpty(cervejaFilter.getNome())){
            Expression x = builder.lower(root.get(Cerveja_.nome));
            Predicate p = builder.like(x, "%" + cervejaFilter.getNome().toLowerCase() + "%");
            predicates.add(builder.and(p));
        }

        if (!StringUtils.isEmpty(cervejaFilter.getEstilo())){
            Predicate p = builder.equal(root.get(Cerveja_.estilo), cervejaFilter.getEstilo());
            predicates.add(builder.and(p));
        }

        if (!StringUtils.isEmpty(cervejaFilter.getSabor())){
            Predicate p = builder.equal(root.get(Cerveja_.sabor), cervejaFilter.getSabor());
            predicates.add(builder.and(p));
        }

        if (!StringUtils.isEmpty(cervejaFilter.getOrigem())){
            Predicate p = builder.equal(root.get(Cerveja_.origem), cervejaFilter.getOrigem());
            predicates.add(builder.and(p));
        }

        if (!StringUtils.isEmpty(cervejaFilter.getValorDe())){
            Predicate p = builder.ge(root.get(Cerveja_.valor), cervejaFilter.getValorDe());
            predicates.add(builder.and(p));
        }

        if (!StringUtils.isEmpty(cervejaFilter.getValorAte())){
            Predicate p = builder.le(root.get(Cerveja_.valor), cervejaFilter.getValorAte());
            predicates.add(builder.and(p));
        }

        Predicate temp[] = new Predicate[predicates.size()];
        predicates.toArray(temp);
        Predicate and = builder.and(temp);
        return and;
    }

    private long count(CervejaFilter cervejaFilter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<Cerveja> root = criteria.from(Cerveja.class);

        criteria.where(filter(cervejaFilter, builder, root));
        criteria.select(builder.count(root.get(Cerveja_.id)));

        TypedQuery<Long> query = entityManager.createQuery(criteria);
        return query.getSingleResult();
    }
}
