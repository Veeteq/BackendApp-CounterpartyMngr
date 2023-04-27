package com.veeteq.finance.counterparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.veeteq.finance.counterparty.model")
@EnableJpaRepositories(basePackages = "com.veeteq.finance.counterparty.repository")
public class CounterpartyMngrApp {
        
    public static void main(String[] args) {
        SpringApplication.run(CounterpartyMngrApp.class, args);
    }

}
