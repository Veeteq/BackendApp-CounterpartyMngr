package com.veeteq.finance.counterparty.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.UUID;

@Service
public class MessageQueueService {

  private final JmsTemplate jmsTemplate;

  @Autowired
  public MessageQueueService(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  public void sendBankStatmentUpdate(Long bankStatementDetailId, Long counterpartyId, Destination replyTo) {
    jmsTemplate.send(replyTo, new MessageCreator() {
      @Override
      public Message createMessage(Session session) {
        try {
          MapMessage message = session.createMapMessage();
          message.setJMSCorrelationID(UUID.randomUUID().toString());
          message.setLong("bankStatementDetailId", bankStatementDetailId);
          message.setLong("counterpartyId", counterpartyId);
          return message;
        } catch (JMSException exc) {
          throw new RuntimeException(exc.getMessage(), exc);
        }
      }
    });
  }
}
