package com.example.paymentservice.controllers;

import com.example.paymentservice.dto.request.PaymentRequest;
import com.example.paymentservice.exception.ResourceNotFoundException;
import com.example.paymentservice.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Validated
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok().body(paymentService.createPayment(paymentRequest));
    }

    @GetMapping("/payment/{uid}")
    public ResponseEntity<?> getPayment(@PathVariable(value = "uid") UUID uuid) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(paymentService.getPayment(uuid));
    }

    @PutMapping("/payment")
    public ResponseEntity<?> cancelPayment(@RequestHeader(name = "paymentUid") UUID paymentUid) throws ResourceNotFoundException {
        paymentService.cancelPayment(paymentUid);
        return ResponseEntity.noContent().build();
    }

    @KafkaListener(topics = "rental", groupId = "group_id")
    public void cancelPaymentStatusByPaymentUid(String paymentUid) throws ResourceNotFoundException {
        System.out.println("Message accepted from kafka queue...");
        paymentService.cancelPayment(UUID.fromString(paymentUid));
    }
}
