package com.veeteq.finance.counterparty.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.veeteq.finance.counterparty.service.CounterpartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.veeteq.finance.counterparty.dto.BankDataDTO;


@Component
public class MessageQueueListener {
    private final static Logger LOG = LoggerFactory.getLogger(MessageQueueListener.class);

    private final ObjectMapper mapper = new ObjectMapper();

    private final CounterpartyService counterpartyService;
    private final MessageQueueService messageQueueService;

    @Value("${artemis.counterparty.request.queue}")
    private String requestQueue;

    @Autowired
    public MessageQueueListener(CounterpartyService counterpartyService, MessageQueueService messageQueueService) {
        this.counterpartyService = counterpartyService;
        this.messageQueueService = messageQueueService;
    }

    @JmsListener(destination = "${artemis.counterparty.request.queue}")
    public void receiveMessage(@Payload String detail, @Headers MessageHeaders headers, Message message, Session session) {
        LOG.info("received: " + detail);

        try {
            BankDataDTO bankData = mapper.readValue(detail, BankDataDTO.class);
            Long counterpartyId = counterpartyService.searchByBankData(bankData);

            if (counterpartyId > 0) {
                messageQueueService.sendBankStatmentUpdate(bankData.getId(), counterpartyId, message.getJMSReplyTo());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                message.acknowledge();
            } catch (JMSException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
}
