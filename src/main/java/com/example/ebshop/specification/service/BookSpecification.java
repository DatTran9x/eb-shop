package com.example.ebshop.specification.service;

import com.example.ebshop.entity.Author;
import com.example.ebshop.entity.Book;
import com.example.ebshop.entity.Publisher;
import com.example.ebshop.specification.model.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import javax.persistence.criteria.*;
import java.math.BigDecimal;

public class BookSpecification implements Specification<Book> {

    private final SearchCriteria criteria;

    public BookSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }


    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if(ObjectUtils.isEmpty(criteria.getValue())){
            return builder.isFalse(root.get("deleted"));
        }
        if (criteria.getOperator().equalsIgnoreCase("like")) {
            return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue().toString() + "%");
        }
        if (criteria.getOperator().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), (BigDecimal) criteria.getValue());
        }
        if (criteria.getOperator().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), (BigDecimal) criteria.getValue());
        }
        if (criteria.getOperator().equalsIgnoreCase("=")) {
            if (criteria.getKey().equalsIgnoreCase("author")) {
                Join<Book, Author> join = root.join("author", JoinType.LEFT);
                return builder.like(join.get("id"), criteria.getValue().toString());
            }
            if (criteria.getKey().equalsIgnoreCase("publisher")) {
                Join<Book, Publisher> join = root.join("publisher", JoinType.LEFT);
                return builder.like(join.get("id"), criteria.getValue().toString());
            }
            return builder.equal(root.get(criteria.getKey()), criteria.getValue().toString());
        }
        return builder.isFalse(root.get("delete"));
    }
}
