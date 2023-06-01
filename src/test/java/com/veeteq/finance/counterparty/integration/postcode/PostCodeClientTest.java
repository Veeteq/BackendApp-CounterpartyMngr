package com.veeteq.finance.counterparty.integration.postcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableFeignClients
class PostCodeClientTest {

    @Autowired
    private PostCodeClient client;
    
    @Test
    void testFindByPostCode() {
        List<PostCodeResponseDTO> response = client.findByPostCode("53-024");
        
        assertNotNull(response);
        assertEquals(3, response.size());
        
        PostCodeResponseDTO location = response.get(0);
        assertEquals("53-024", location.getPostcode());
        assertEquals("Wrocław", location.getCity());
    }

}
