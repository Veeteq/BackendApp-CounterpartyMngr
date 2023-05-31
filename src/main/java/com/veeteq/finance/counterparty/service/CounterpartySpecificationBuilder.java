package com.veeteq.finance.counterparty.service;

import org.springframework.data.jpa.domain.Specification;

import com.veeteq.finance.counterparty.model.Counterparty;

public class CounterpartySpecificationBuilder {
/*
    private final List<SearchCriteria> params;

    public CounterpartySpecificationBuilder() {
        params = new LinkedList<>();
    }
    
    public CounterpartySpecificationBuilder with(String key, String operation, String value, String prefix, String suffix) {
      return with(null, key, operation, value, prefix, suffix);
    }
    
    
    public CounterpartySpecificationBuilder with(String orPredicate, String key, String operation, String value, String prefix, String suffix) {
        params.add(new SearchCriteria(key, value));
        return this;
    }

    public Specification<Counterparty> build() {
        if (params.size() == 0) return null;
        
        
        Specification<Counterparty> result = new CounterpartySpecification(params.get(0));
        
        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).or(new CounterpartySpecification(params.get(i)));  
        }
        
        return result;
    }
*/
    
    public static Specification<Counterparty> hasId() {
        return (counterparty, query, criteriaBuilder) -> criteriaBuilder.isNotNull(counterparty.get("id"));        
    }
    
    public static Specification<Counterparty> hasIban(String iban) {
        return (counterparty, query, criteriaBuilder) -> criteriaBuilder.equal(counterparty.get("bankAccountNumber"), iban);
    }
    
    public static Specification<Counterparty> hasNip(String nip) {
        return (counterparty, query, criteriaBuilder) -> criteriaBuilder.equal(counterparty.get("nip"), nip);
    }
    
}
