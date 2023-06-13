package com.veeteq.finance.counterparty.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * https://memorynotfound.com/spring-boot-embedded-activemq-configuration-example/
 * https://aniruthmp.github.io/Spring-JMS-request-response/
 */


@Configuration
@EnableJms
public class JmsConfiguration {
    public static final String COUNTERPARTY_RESPONSE_QUEUE = "cprt-resp-queue";
        
    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        configurer.configure(factory, connectionFactory);

        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        factory.setErrorHandler(t -> System.out.println("An exception occured " + t.getMessage()));        
        //factory.setMessageConverter(messageConverter());


        return factory;
    }
/*
    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.OBJECT);
        converter.setTypeIdPropertyName("msgTypeId");
        return converter;
    }
*/
}
