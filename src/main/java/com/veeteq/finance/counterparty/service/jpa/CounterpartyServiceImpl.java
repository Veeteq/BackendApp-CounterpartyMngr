package com.veeteq.finance.counterparty.service.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import com.veeteq.finance.counterparty.dto.BankDataDTO;
import com.veeteq.finance.counterparty.dto.CounterpartyDTO;
import com.veeteq.finance.counterparty.dto.PageResponse;
import com.veeteq.finance.counterparty.exception.ResourceNotFoundException;
import com.veeteq.finance.counterparty.fileutils.export.DataExporter;
import com.veeteq.finance.counterparty.fileutils.export.DataExporterFactory;
import com.veeteq.finance.counterparty.mapper.CounterpartyMapper;
import com.veeteq.finance.counterparty.model.Counterparty;
import com.veeteq.finance.counterparty.repository.CounterpartyRepository;
import com.veeteq.finance.counterparty.search.SearchCriteria;
import com.veeteq.finance.counterparty.service.CounterpartyFinder;
import com.veeteq.finance.counterparty.service.CounterpartyService;

@Service
public class CounterpartyServiceImpl implements CounterpartyService {
    private final Logger LOG = LoggerFactory.getLogger(CounterpartyServiceImpl.class);
    private final Long FAKE_ID = Long.valueOf(-1);

    private final CounterpartyMapper mapper;
    private final CounterpartyRepository counterpartyRepository;
    private final CounterpartyFinder counterpartyFinder;

    @Autowired
    public CounterpartyServiceImpl(CounterpartyRepository counterpartyRepository,
                                   CounterpartyMapper counterpartyMapper,
                                   CounterpartyFinder counterpartyFinder) {
        this.counterpartyRepository = counterpartyRepository;
        this.mapper = counterpartyMapper;
        this.counterpartyFinder = counterpartyFinder;
    }

    @Override
    public PageResponse<CounterpartyDTO> findAll(PageRequest pageRequest) {

        Page<Counterparty> page = counterpartyRepository.findAllWithTags(pageRequest);
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
    @Transactional
    public CounterpartyDTO save(CounterpartyDTO dto) {
        LOG.info("Saving a new counterparty");

        Counterparty entity = mapper.toEntity(dto);

        Counterparty savedCounterparty = counterpartyRepository.save(entity);

        return mapper.toDto(savedCounterparty);
    }

    @Override
    @Transactional
    @Lock(LockModeType.OPTIMISTIC)
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

    @Override
    public Long searchByBankData(BankDataDTO data) {
        LOG.info("Search using BankData");

        Map<String, String> searchCriteria = SearchCriteria.buildSearchCriteria(data);
        List<Long> records = this.counterpartyFinder.getRecords(searchCriteria);
        LOG.info("records: " + records.size());

        if (records.size() != 1) {
            return FAKE_ID; //Return fake ID
        }

        return records.get(0); //Return the ID of single element in collection
    }

    @Override
    public List<CounterpartyDTO> searchByNameIbanTaxId(Map<String, String> searchParams) {
        LOG.info("Search using search params");

        Map<String, String> searchCriteria = new HashMap<>();
        String[] searchAttributes = {"name", "iban", "taxId"};
        for (String key : searchAttributes) {
            String value = searchParams.get(key);
            if (value != null) {
                searchCriteria.put(key, value);
            }
        }
        List<CounterpartyDTO> counterparties = this.counterpartyFinder.searchByCriteria(searchCriteria)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return counterparties;
    }

    @Override
    public void exportData() {
        LOG.info("Exporting counterparties to file");

        DataExporter exporter = DataExporterFactory.getExporter();
        exporter.setDataSource(counterpartyRepository);
        exporter.exportData();
    }

}
