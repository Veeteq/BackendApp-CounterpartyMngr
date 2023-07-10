package com.veeteq.finance.counterparty.service;

import org.springframework.data.domain.PageRequest;

import com.veeteq.finance.counterparty.dto.BankDataDTO;
import com.veeteq.finance.counterparty.dto.CounterpartyDTO;
import com.veeteq.finance.counterparty.dto.PageResponse;
import com.veeteq.finance.counterparty.exception.ResourceNotFoundException;
import com.veeteq.finance.counterparty.model.Counterparty;

import java.util.List;
import java.util.Map;

public interface CounterpartyService {

    PageResponse<CounterpartyDTO> findAll(PageRequest pageRequest);

    CounterpartyDTO findById(Long id);

    CounterpartyDTO save(CounterpartyDTO dto);

    CounterpartyDTO update(Long id, CounterpartyDTO dto) throws ResourceNotFoundException;

    void delete(Counterparty entity);

    void deleteById(Long id) throws ResourceNotFoundException;

    Long searchByBankData(BankDataDTO data);

    void exportData();

    List<CounterpartyDTO> searchByNameIbanTaxId(Map<String, String> searchParams);
}
