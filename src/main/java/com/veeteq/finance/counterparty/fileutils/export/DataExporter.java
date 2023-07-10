package com.veeteq.finance.counterparty.fileutils.export;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veeteq.finance.counterparty.model.Counterparty;

public interface DataExporter {

    void exportData();
    void setDataSource(JpaRepository<Counterparty, Long> counterpartyRepository);


}
