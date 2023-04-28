package com.veeteq.finance.counterparty.service.jpa;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veeteq.finance.counterparty.dto.CounterpartyDTO;
import com.veeteq.finance.counterparty.exception.ResourceNotFoundException;
import com.veeteq.finance.counterparty.mapper.CounterpartyMapper;
import com.veeteq.finance.counterparty.model.Counterparty;
import com.veeteq.finance.counterparty.repository.CounterpartyRepository;
import com.veeteq.finance.counterparty.service.CounterpartyService;

@Service
public class CounterpartyServiceImpl implements CounterpartyService {
    private final Logger LOG = LoggerFactory.getLogger(CounterpartyServiceImpl.class);
    
    private final CounterpartyMapper mapper = new CounterpartyMapper();
    private final CounterpartyRepository counterpartyRepository;
    
    @Autowired
    public CounterpartyServiceImpl(CounterpartyRepository counterpartyRepository) {
        this.counterpartyRepository = counterpartyRepository;
    }

    @Override
    public List<CounterpartyDTO> findAll() {
        return counterpartyRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CounterpartyDTO findById(Long id) {
        return counterpartyRepository
                .findById(id)
                .map(mapper::toDto)
                .orElseThrow();
    }

    @Override
    public CounterpartyDTO save(CounterpartyDTO dto) {
        Counterparty entity = mapper.toEntity(dto);
        
        Counterparty savedCounterparty = counterpartyRepository.save(entity);
        
        return mapper.toDto(savedCounterparty);
    }

    @Override
    @Transactional
    public CounterpartyDTO update(Long id, @Valid CounterpartyDTO dto) throws ResourceNotFoundException {
        LOG.info("Updating a counterparty with id: " + id);
        
        Counterparty entity = mapper.toEntity(dto);
        
        return counterpartyRepository.findById(id)
                .map(oldItem -> {
                  Counterparty updated = oldItem.updateWith(entity);
                  Counterparty saved = counterpartyRepository.save(updated);
                  return mapper.toDto(saved);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Counterparty not found for this id :: " + id));
    }

    @Override
    public void delete(Counterparty entity) {
        counterpartyRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        counterpartyRepository.deleteById(id);
        Counterparty savedEntity = counterpartyRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Counterparty not found for this id :: " + id));

        this.delete(savedEntity);
    }

}
