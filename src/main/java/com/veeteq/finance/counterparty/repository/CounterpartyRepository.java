package com.veeteq.finance.counterparty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.veeteq.finance.counterparty.model.Counterparty;

public interface CounterpartyRepository extends JpaRepository<Counterparty, Long>, JpaSpecificationExecutor<Counterparty>{

    List<Counterparty> findByFullNameContainingIgnoreCase(String fullName);
}
