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

    @Column(name = "cprt_tax_tx")
    private String taxId;

    @Column(name = "cprt_iban_tx")
    private String iban;

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

    public String getTaxId() {
        return taxId;
    }

    public Counterparty setTaxId(String taxId) {
        if (taxId != null) {
            //Remove non-numeric characters
            taxId = taxId.replaceAll("[^0-9.]", "");
        }
        this.taxId = taxId;
        return this;
    }

    public String getIban() {
        return iban;
    }

    public Counterparty setIban(String iban) {
        this.iban = iban;
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
                .setIban(counterparty.getIban())
                .setTaxId(counterparty.getTaxId())
                .setTags(counterparty.getTags());
    }

}