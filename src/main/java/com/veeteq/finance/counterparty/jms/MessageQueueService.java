package com.veeteq.finance.counterparty.jms;

import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

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
