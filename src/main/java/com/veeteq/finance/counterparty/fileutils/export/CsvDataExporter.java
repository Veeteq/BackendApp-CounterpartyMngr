package com.veeteq.finance.counterparty.fileutils.export;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veeteq.finance.counterparty.model.Address;
import com.veeteq.finance.counterparty.model.Counterparty;

public class CsvDataExporter implements DataExporter {

    private final CsvWriter writer = new CounterpartyWriter();

    private JpaRepository<Counterparty, Long> counterpartyRepository;

    @Override
    public void setDataSource(JpaRepository<Counterparty, Long> counterpartyRepository) {
        this.counterpartyRepository = counterpartyRepository;
    }



    @Override
    public void exportData() {
        if (counterpartyRepository == null) {
            throw new IllegalStateException("repository must not be null");
        }

        List<CounterpartyCsvBean> data = counterpartyRepository.findAll().stream().map(cprt -> {
            Address address = cprt.getAddress();
            CounterpartyCsvBean bean = new CounterpartyCsvBean()
                    .setId(cprt.getId())
                    .setFullName(cprt.getFullName())
                    .setShortName(cprt.getShortName())
                    .setNip(cprt.getNip())
                    .setIban(cprt.getIban());
            if (address != null) {
                bean.setStreet(address.getStreet())
                    .setPostalCode(address.getPostalCode())
                    .setCity(address.getCity())
                    .setCountry(address.getCountry());
            }

            return bean;
        }).collect(Collectors.toList());
        writer.writeToFile(data, "export.csv");
    }

}
