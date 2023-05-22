package com.veeteq.finance.counterparty.service.jpa;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.veeteq.finance.counterparty.dto.BankDataDTO;
import com.veeteq.finance.counterparty.dto.CounterpartyDTO;
import com.veeteq.finance.counterparty.dto.PageResponse;
import com.veeteq.finance.counterparty.exception.ResourceNotFoundException;
import com.veeteq.finance.counterparty.mapper.CounterpartyMapper;
import com.veeteq.finance.counterparty.model.Counterparty;
import com.veeteq.finance.counterparty.repository.CounterpartyRepository;
import com.veeteq.finance.counterparty.service.CounterpartyService;
import com.veeteq.finance.counterparty.service.SpecificationBuilder;

@Service
public class CounterpartyServiceImpl implements CounterpartyService {
    private final Logger LOG = LoggerFactory.getLogger(CounterpartyServiceImpl.class);
    
    private final CounterpartyMapper mapper;
    private final CounterpartyRepository counterpartyRepository;
    
    @Autowired
    public CounterpartyServiceImpl(CounterpartyRepository counterpartyRepository, CounterpartyMapper counterpartyMapper) {
        this.counterpartyRepository = counterpartyRepository;
        this.mapper = counterpartyMapper;
    }

    @Override
    public PageResponse<CounterpartyDTO> findAll(PageRequest pageRequest) {
        
        Page<Counterparty> page = counterpartyRepository.findAll(pageRequest);
        List<CounterpartyDTO> content = page.get()
            .map(mapper::toDto)
            .collect(Collectors.toList());
        
        PageResponse<CounterpartyDTO> response = new PageResponse<CounterpartyDTO>()
                .setContent(content)
                .setPageNo(page.getNumber())
                .setPageSize(page.getSize())
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages())
                .setLast(page.isLast());

        return response;        
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
        LOG.info("Saving a new counterparty");

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
        LOG.info("Deleting a counterparty with id: " + id);

        Counterparty savedEntity = counterpartyRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Counterparty not found for this id :: " + id));

        this.delete(savedEntity);
    }

    public Long searchByBankData(BankDataDTO data) {
        Specification<Counterparty> specification = SpecificationBuilder.build(data);
        Counterparty result = counterpartyRepository.findAll(specification).stream()
                .findFirst()
                .orElse(new Counterparty().setId(123L));
        return result.getId();
    }
}
