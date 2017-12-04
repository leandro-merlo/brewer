package org.manzatech.brewer.repository.pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;

@Component
public class PaginationUtil {


    public TypedQuery preparar(CriteriaQuery criteria, Pageable pageable, CriteriaBuilder builder, Path root, EntityManager entityManager){
        Sort sort = pageable.getSort();
        if (sort.isSorted()){
            Sort.Order order = sort.iterator().next();
            String field = order.getProperty();
            criteria.orderBy(order.isAscending() ? builder.asc(root.get(field)) : builder.desc(root.get(field)));
        }

        TypedQuery query = entityManager.createQuery(criteria);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        return query;
    }
}
