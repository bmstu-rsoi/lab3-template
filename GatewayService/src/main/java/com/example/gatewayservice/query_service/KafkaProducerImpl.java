package com.example.gatewayservice.query_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaProducerImpl implements KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessageToCarService(UUID carUid) {
        System.out.println("Sending message to kafka..." + carUid);
        kafkaTemplate.send("rental", carUid.toString());
    }
}
