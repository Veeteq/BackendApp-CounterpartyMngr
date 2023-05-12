package com.veeteq.finance.counterparty.dataloader;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veeteq.finance.counterparty.dataloader.pojo.Result;
import com.veeteq.finance.counterparty.dataloader.pojo.Root;
import com.veeteq.finance.counterparty.dto.CounterpartyDTO;
import com.veeteq.finance.counterparty.mapper.CounterpartyMapper;
import com.veeteq.finance.counterparty.model.Counterparty;
import com.veeteq.finance.counterparty.repository.CounterpartyRepository;

@Component
@Profile(value = {"default"})
public class DataLoader implements CommandLineRunner {

    @Value(value = "${dataloader.path}")
    private String dataPath;
    
    @Value(value = "${dataloader.filename}")
    private String dataFilename;
    
    private final CounterpartyMapper mapper;
    private final CounterpartyRepository repository;
    
    @Autowired
    public DataLoader(CounterpartyMapper counterpartyMapper, CounterpartyRepository counterpartyRepository) {
        this.mapper = counterpartyMapper;
        this.repository = counterpartyRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Path dataFile = Paths.get(dataPath).resolve(dataFilename);
        
        InputStream inputStream = Files.newInputStream(dataFile, StandardOpenOption.READ);
        ObjectMapper mapper = new ObjectMapper();
        Root root = mapper.readValue(inputStream, Root.class);        
        inputStream.close();
        
        saveData(root.getResults().get(0));
    }

    private void saveData(Result result) {

        List<Counterparty> saved = result.getItems().stream()
                .sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
                .filter(item -> item.getId() > 2)
                .map(item -> {
                    CounterpartyDTO dto = new CounterpartyDTO();
                    BeanUtils.copyProperties(item, dto);
                    return dto;
                })
                .map(mapper::toEntity)
                .map(repository::save)
                .collect(Collectors.toList());

        System.out.println("Data saved count: " + saved.size());
        System.out.println("Total elements in db: " + repository.count());
    }

}
