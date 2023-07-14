package com.veeteq.finance.counterparty.search;

import org.springframework.data.jpa.domain.Specification;

import com.veeteq.finance.counterparty.model.Counterparty;

public class CounterpartySpecificationBuilder {
    
    public static Specification<Counterparty> hasId() {
        return (counterparty, query, criteriaBuilder) -> criteriaBuilder.isNotNull(counterparty.get("id"));        
    }
    
    public static Specification<Counterparty> hasIban(String iban) {
        return (counterparty, query, criteriaBuilder) -> criteriaBuilder.equal(counterparty.get("iban"), iban);
    }
    
    public static Specification<Counterparty> hasNip(String taxId) {
        return (counterparty, query, criteriaBuilder) -> criteriaBuilder.equal(counterparty.get("taxId"), taxId);
    }
    
}
