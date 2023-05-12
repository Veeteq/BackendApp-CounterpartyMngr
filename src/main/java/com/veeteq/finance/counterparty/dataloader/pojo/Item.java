package com.veeteq.finance.counterparty.dataloader.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {

    @JsonProperty(value = "cprt_id")
    private Long id;

    @JsonProperty(value = "cprt_name_tx")
    private String fullname;

    @JsonProperty(value = "cprt_shrt_name_tx")
    private String shortname;

    @JsonProperty(value = "cprt_nip_tx")
    private String nip;
    
    @JsonProperty(value = "addr_stre_tx")
    private String street;
    
    @JsonProperty(value = "addr_post_tx")
    private String postcode;
    
    @JsonProperty(value = "addr_city_tx")
    private String city;
    
    @JsonProperty(value = "cprt_acco_numb_tx")
    private String iban;
    
    @JsonProperty(value = "addr_cntr_tx")
    private String country;

    public Long getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getShortname() {
        return shortname;
    }

    public String getNip() {
        return nip;
    }

    public String getStreet() {
        return street;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    public String getIban() {
        return iban;
    }

    public String getCountry() {
        return country;
    }

}
