package com.veeteq.finance.counterparty.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.veeteq.finance.counterparty.model.Counterparty;

import java.util.Map;

public class CounterpartySpecification { //implements Specification<Counterparty> {
    private static final long serialVersionUID = 1467103650418120061L;

    private Map<String, Object> criteria;
    
    public CounterpartySpecification(Map<String, Object> criteria) {
        this.criteria = criteria;
    }

}
