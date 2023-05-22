package com.veeteq.finance.counterparty.service;

import org.springframework.data.jpa.domain.Specification;

import com.veeteq.finance.counterparty.dto.BankDataDTO;
import com.veeteq.finance.counterparty.model.Counterparty;

public class SpecificationBuilder {

    public static Specification<Counterparty> build(BankDataDTO data) {
        return Specification.where(hasCounterpartyIban(data.getAccountNumber()));
    }

    private static Specification<Counterparty> hasCounterpartyIban(String iban) {
        return (counterparty, query, criteriaBuilder) -> criteriaBuilder.equal(counterparty.get("bankAccountNumber"), iban);
    }
}
