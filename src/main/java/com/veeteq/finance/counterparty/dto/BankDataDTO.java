package com.veeteq.finance.counterparty.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class BankDataDTO {

    // private Long id;
    // private Integer sequenceNumber;
    // private LocalDate operationDate;
    // private String operationType;
    // private LocalDate postingDate;
    private String title;
    private String accountNumber;
    private String counterparty;
    private String counterpartyAddress;

    // private BigDecimal amount;
    // private BigDecimal balance;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public String getCounterpartyAddress() {
        return counterpartyAddress;
    }

    public void setCounterpartyAddress(String counterpartyAddress) {
        this.counterpartyAddress = counterpartyAddress;
    }

}
