package com.veeteq.finance.counterparty.integration.postcode;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"kod",
"miejscowosc",
"ulica",
"gmina",
"powiat",
"wojewodztwo",
"dzielnica",
"numeracja"
})
public class PostCodeResponseDTO {

    @JsonProperty("kod")
    public String postcode;
    
    @JsonProperty("miejscowosc")
    public String city;
    
    @JsonProperty("ulica")
    public String street;
    
    @JsonProperty("gmina")
    public String community;
    
    @JsonProperty("powiat")
    public String county;
    
    @JsonProperty("wojewodztwo")
    public String province;
    
    @JsonProperty("dzielnica")
    public String district;
    
    @JsonProperty("numeracja")
    public List<String> range;

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getCommunity() {
        return community;
    }

    public String getCounty() {
        return county;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public List<String> getRange() {
        return range;
    }
    
}
