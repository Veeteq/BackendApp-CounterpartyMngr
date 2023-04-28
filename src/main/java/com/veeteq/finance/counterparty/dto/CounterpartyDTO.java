package com.veeteq.finance.counterparty.dto;

public class CounterpartyDTO {

    private Long id;
    private String fullname;
    private String shortname;
    private String nip;
    private String street;
    private String city;
    private String postcode;
    private String country;
    private String iban;

    public Long getId() {
        return id;
    }

    public CounterpartyDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullname() {
        return fullname;
    }

    public CounterpartyDTO setFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public String getShortname() {
        return shortname;
    }

    public CounterpartyDTO setShortname(String shortname) {
        this.shortname = shortname;
        return this;
    }

    public String getNip() {
        return nip;
    }

    public CounterpartyDTO setNip(String nip) {
        this.nip = nip;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public CounterpartyDTO setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getCity() {
        return city;
    }

    public CounterpartyDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostcode() {
        return postcode;
    }

    public CounterpartyDTO setPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public CounterpartyDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getIban() {
        return iban;
    }

    public CounterpartyDTO setIban(String iban) {
        this.iban = iban;
        return this;
    }

}
