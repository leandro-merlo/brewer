package org.manzatech.brewer.repository.helpers.cidade;

import org.manzatech.brewer.model.Cidade;
import org.manzatech.brewer.model.Cidade_;
import org.manzatech.brewer.model.Estado;
import org.manzatech.brewer.repository.filters.CidadeFilter;
import org.manzatech.brewer.repository.pagination.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;

public class CidadesImpl implements CidadesQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PaginationUtil paginationUtil;

    @Override
    public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cidade> criteria = builder.createQuery(Cidade.class);
        Root<Cidade> root = criteria.from(Cidade.class);

        Fetch<Cidade, Estado> end = root.fetch(Cidade_.estado, JoinType.LEFT);

        criteria.where(filter(cidadeFilter, builder, root));

        TypedQuery query = null;
        Sort s = pageable.getSort();
        if (s.isSorted()) {
            Sort.Order order = s.iterator().next();
            if (order.getProperty().equals("estado")){
                PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(order.getDirection(), "nome"));
                query = paginationUtil.preparar(criteria, pr, builder, root.get(Cidade_.estado), entityManager);
            }
        }
        if (query == null) {
            query = paginationUtil.preparar(criteria, pageable, builder, root, entityManager);
        }
        PageImpl<Cidade> page = new PageImpl<Cidade>(query.getResultList(), pageable, count(cidadeFilter));
        return page;
    }

    private Predicate filter(CidadeFilter cidadeFilter, CriteriaBuilder builder, Path root) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(cidadeFilter.getNome())) {
            Expression x = builder.lower(root.get(Cidade_.nome));
            Predicate p = builder.like(x, "%" + cidadeFilter.getNome().toLowerCase() + "%");
            predicates.add(builder.and(p));
        }

        if (!StringUtils.isEmpty(cidadeFilter.getEstado())) {
            Predicate p = builder.equal(root.get(Cidade_.estado), cidadeFilter.getEstado());
            predicates.add(builder.and(p));
        }

        Predicate temp[] = new Predicate[predicates.size()];
        predicates.toArray(temp);
        Predicate and = builder.and(temp);
        return and;
    }

    private long count(CidadeFilter cidadeFilter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<Cidade> root = criteria.from(Cidade.class);

        Join<Cidade, Estado> end = root.join(Cidade_.estado, JoinType.LEFT);

        criteria.where(filter(cidadeFilter, builder, root));
        criteria.select(builder.count(root.get(Cidade_.id)));

        TypedQuery<Long> query = entityManager.createQuery(criteria);
        return query.getSingleResult();
    }
}
