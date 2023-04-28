package com.veeteq.finance.counterparty.service;

import java.util.List;

import com.veeteq.finance.counterparty.dto.CounterpartyDTO;
import com.veeteq.finance.counterparty.exception.ResourceNotFoundException;
import com.veeteq.finance.counterparty.model.Counterparty;

public interface CounterpartyService {

    List<CounterpartyDTO> findAll();
    
    CounterpartyDTO findById(Long id);
    
    CounterpartyDTO save(CounterpartyDTO dto);
    
    CounterpartyDTO update(Long id, CounterpartyDTO dto) throws ResourceNotFoundException;

    void delete(Counterparty entity);
    
    void deleteById(Long id) throws ResourceNotFoundException;

}
