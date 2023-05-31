package com.veeteq.finance.counterparty.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.veeteq.finance.counterparty.model.Counterparty;

public interface CounterpartyRepository extends JpaRepository<Counterparty, Long>, JpaSpecificationExecutor<Counterparty>{

    @Query(value = "SELECT c FROM Counterparty c "
                   + "LEFT JOIN FETCH c.tags t ",
           countQuery = "SELECT COUNT(c) FROM Counterparty c")
    Page<Counterparty> findAllWithTags(PageRequest pageRequest);

    List<Counterparty> findByFullNameContainingIgnoreCase(String fullName);

    @Query("SELECT c FROM Counterparty c "
           + "LEFT JOIN c.tags t "
          + "WHERE t = :tag ")
    List<Counterparty> findByTag(String tag);

    List<Counterparty> findByBankAccountNumber(String bankAccountNumber);
}
