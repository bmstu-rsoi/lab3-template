package com.example.gatewayservice.controller;

import com.example.gatewayservice.dto.req.PaymentRequest;
import com.example.gatewayservice.dto.resp.AnswerResp;
import com.example.gatewayservice.dto.resp.PaymentResponse;
import com.example.gatewayservice.feign_service.PaymentService;
import com.example.gatewayservice.query_service.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {
    private final PaymentService paymentService;
    @Autowired
    private final KafkaProducer kafkaProducerService;

    private final TaskScheduler scheduler;

    private static final Integer N = 10;
    private Integer errorsNumber = 0;
    private final Runnable healthCheck =
            new Runnable() {
                @Override
                public void run() {
                    try {
                        paymentService.ping();
                        System.out.println("Car healthCheck passed");
                        errorsNumber = 0;
                    } catch (Exception e) {
                        System.out.println("Car healthCheck for errors: " + errorsNumber);
                        scheduler.schedule(this, new Date(System.currentTimeMillis() + 10000L));
                    }
                }
            };


    public PaymentController(PaymentService paymentService, KafkaProducer kafkaProducerService, TaskScheduler scheduler) {
        this.paymentService = paymentService;
        this.kafkaProducerService = kafkaProducerService;
        this.scheduler = scheduler;
    }

    @GetMapping("/{paymentUid}")
    public ResponseEntity<?> getPaymentInfoByPaymentUid(@PathVariable UUID paymentUid) {
        PaymentResponse result;
        try {
            if (errorsNumber >= N) {
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Payment Service unavailable"));
            } else {
                result = paymentService.getPaymentInfoByPaymentUid(paymentUid);
                if (result != null) errorsNumber = 0;
            }
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Payment Service unavailable"));
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse result;
        try {
            result = paymentService.createPayment(paymentRequest);
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Payment Service unavailable"));
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("")
    public ResponseEntity<?> cancelPaymentStatusByPaymentUid(@RequestHeader(name = "paymentUid") UUID paymentUid) {
        try {
            kafkaProducerService.sendMessageToCarService(paymentUid);
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Payment Service unavailable"));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
