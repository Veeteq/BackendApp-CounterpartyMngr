package com.veeteq.finance.counterparty.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.veeteq.finance.counterparty.model.Counterparty;

public class CounterpartySpecification implements Specification<Counterparty> {
    private static final long serialVersionUID = 1467103650418120061L;

    private SearchCriteria criteria;
    
    public CounterpartySpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Counterparty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getKey());
    }

}
