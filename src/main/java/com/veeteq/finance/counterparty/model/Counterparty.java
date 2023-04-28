package com.veeteq.finance.counterparty.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Table(name = "counterparties")
public class Counterparty extends BaseEntity<Counterparty> {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cprt_id")
    private Long id;

    @Column(name = "cprt_name_tx")
    private String fullName;
    
    @Column(name = "cprt_shrt_name_tx")
    private String shortName;

    @Column(name = "cprt_nip_tx")
    private String nip;
    
    @Column(name = "cprt_acco_numb_tx")
    private String bankAccountNumber;
    
    @Embedded
    private Address address;

    public Long getId() {
        return this.id;
    }
    
    public Counterparty setId(Long id) {
        this.id = id;
        return this;
    }
    
    public String getFullName() {
        return this.fullName;
    }
    
    public Counterparty setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
    
    public String getShortName() {
        return shortName;
    }

    public Counterparty setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getNip() {
        return nip;
    }

    public Counterparty setNip(String nip) {
        this.nip = nip;
        return this;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public Counterparty setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public Counterparty setAddress(Address address) {
        this.address = address;
        return this;
    }

    public Counterparty updateWith(@Valid Counterparty counterparty) {

        return this
                .setFullName(counterparty.getFullName())
                .setShortName(counterparty.getShortName())
                .setAddress(counterparty.getAddress())
                .setBankAccountNumber(counterparty.getBankAccountNumber())
                .setNip(counterparty.getNip());
    }

}