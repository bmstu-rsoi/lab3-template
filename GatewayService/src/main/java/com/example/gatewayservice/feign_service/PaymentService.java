package com.example.gatewayservice.feign_service;

import com.example.gatewayservice.dto.req.PaymentRequest;
import com.example.gatewayservice.dto.resp.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "payment-service", url = "http://payment:8050")
public interface PaymentService {
    @GetMapping("/manage/health")
    String ping();

    @GetMapping("/api/v1/payment/{paymentUid}")
    PaymentResponse getPaymentInfoByPaymentUid(@PathVariable UUID paymentUid);

    @PostMapping("/api/v1/payment")
    PaymentResponse createPayment(@RequestBody PaymentRequest paymentRequest);

    @PutMapping("/api/v1/payment/{paymentUid}")
    ResponseEntity<?> cancelPaymentStatusByPaymentUid(@PathVariable UUID paymentUid);
}
