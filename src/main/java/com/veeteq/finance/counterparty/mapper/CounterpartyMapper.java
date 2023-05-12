package com.veeteq.finance.counterparty.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.veeteq.finance.counterparty.dto.CounterpartyDTO;
import com.veeteq.finance.counterparty.model.Address;
import com.veeteq.finance.counterparty.model.Counterparty;
import com.veeteq.finance.counterparty.repository.UtilityRepository;

@Component
public class CounterpartyMapper {
    
    private final UtilityRepository utilityRepository;

    @Autowired
    public CounterpartyMapper(UtilityRepository utilityRepository) {
        this.utilityRepository = utilityRepository;
    }

    public CounterpartyDTO toDto(Counterparty entity) {
        CounterpartyDTO dto = new CounterpartyDTO()
                .setId(entity.getId())
                .setFullname(entity.getFullName())
                .setShortname(entity.getShortName())
                .setNip(entity.getNip())
                .setIban(entity.getBankAccountNumber());
                
                Address address = entity.getAddress();
                if (address != null) {
                    dto.setStreet(entity.getAddress().getStreet())
                    .setCity(entity.getAddress().getCity())
                    .setPostcode(entity.getAddress().getPostalCode())
                    .setCountry(entity.getAddress().getCountry());
                }
        return dto;
    }
    
    public Counterparty toEntity(CounterpartyDTO dto) {
        Counterparty entity = new Counterparty()
                .setId(dto.getId() != null ? dto.getId() : utilityRepository.getCounterpartyId())
                .setFullName(dto.getFullname())
                .setShortName(dto.getShortname())
                .setNip(dto.getNip())
                .setBankAccountNumber(dto.getIban())
                .setAddress(toAddress(dto));
        return entity;
    }
    
    public Address toAddress(CounterpartyDTO dto) {
        Address address = new Address()
                .setStreet(dto.getStreet())
                .setCity(dto.getCity())
                .setPostalCode(dto.getPostcode())
                .setCountry(dto.getCountry());
        return address;
    }
}
