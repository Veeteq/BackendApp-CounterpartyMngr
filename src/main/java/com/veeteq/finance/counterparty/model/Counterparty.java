package com.veeteq.finance.counterparty.model;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Table(name = "counterparties")
public class Counterparty extends BaseEntity<Counterparty> {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cprt_id", nullable = false)
    private Long id;

    @Column(name = "cprt_name_tx", nullable = false)
    private String fullName;
    
    @Column(name = "cprt_shrt_name_tx")
    private String shortName;

    @Column(name = "cprt_nip_tx")
    private String nip;
    
    @Column(name = "cprt_acco_numb_tx")
    private String bankAccountNumber;
    
    @Embedded
    private Address address;

    @ElementCollection
    @CollectionTable(name = "counterparty_tags", joinColumns = @JoinColumn(name = "cprt_id", referencedColumnName = "cprt_id")) 
    @Column(name = "cprt_ctag_tx", nullable = false)
    private Set<String> tags = new TreeSet<>();

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
        if (nip != null) {
            //Remove non-numeric characters
            nip = nip.replaceAll("[^0-9.]", "");
        }
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

    public Set<String> getTags() {
        return tags;
    }

    public Counterparty setTags(Set<String> tags) {
        this.tags = tags;
        return this;
    }

    public Counterparty addToTags(String tag) {
        this.tags.add(tag);
        return this;
    }

    public Counterparty updateWith(@Valid Counterparty counterparty) {

        return this
                .setFullName(counterparty.getFullName())
                .setShortName(counterparty.getShortName())
                .setAddress(counterparty.getAddress())
                .setBankAccountNumber(counterparty.getBankAccountNumber())
                .setNip(counterparty.getNip())
                .setTags(counterparty.getTags());
    }

}