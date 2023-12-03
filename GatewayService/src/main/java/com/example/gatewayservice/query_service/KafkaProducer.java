package com.example.gatewayservice.query_service;

import java.util.UUID;

public interface KafkaProducer {
    void sendMessageToCarService(UUID carUid);
}
