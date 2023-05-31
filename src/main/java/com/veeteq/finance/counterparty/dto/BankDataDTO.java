package com.veeteq.finance.counterparty.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class BankDataDTO {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "counterpartyIban")
    private String accountNumber;

    @JsonProperty(value = "counterpartyName")
    private String counterparty;

    @JsonProperty(value = "counterpartyAddress")
    private String counterpartyAddress;

    public String getTitle() {
        return title;
    }

    public BankDataDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BankDataDTO setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public BankDataDTO setCounterparty(String counterparty) {
        this.counterparty = counterparty;
        return this;
    }

    public String getCounterpartyAddress() {
        return counterpartyAddress;
    }

    public BankDataDTO setCounterpartyAddress(String counterpartyAddress) {
        this.counterpartyAddress = counterpartyAddress;
        return this;
    }

}
