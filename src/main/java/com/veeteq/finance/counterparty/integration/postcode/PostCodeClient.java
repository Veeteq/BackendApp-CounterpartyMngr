package com.veeteq.finance.counterparty.integration.postcode;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Kody Pocztowe API
 * Darmowe API pozwalające pobrać adres przy użyciu kodu pocztowego Poczty Polskiej.
 * 
 * Class conntects to https://kodpocztowy.intami.pl/
 * in order to search for addresses based on post code 
 *
 */

@FeignClient(url = "https://kodpocztowy.intami.pl/", name="postcode")
public interface PostCodeClient {

    @GetMapping(path = "/api/{postCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<PostCodeResponseDTO> findByPostCode(@PathVariable("postCode") String postCode);
    
}
