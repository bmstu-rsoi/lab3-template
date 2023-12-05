package com.example.paymentservice.services;

import com.example.paymentservice.dao.Payment;
import com.example.paymentservice.dto.request.PaymentRequest;
import com.example.paymentservice.dto.response.PaymentResponse;
import com.example.paymentservice.enums.PaymentStatus;
import com.example.paymentservice.exception.ResourceNotFoundException;
import com.example.paymentservice.repos.PaymentRepo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepo paymentRepo;

    public PaymentService(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        return new PaymentResponse(paymentRepo.save(new Payment(
                UUID.randomUUID(),
                paymentRequest.getStatus(),
                paymentRequest.getPrice()
        )));
    }

    public void cancelPayment(UUID paymentUid) throws ResourceNotFoundException {
        Payment payment = paymentRepo.
                findByPaymentUid(paymentUid)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with " + paymentUid + "not founded"));
        System.out.println("Payment canceled...");
        payment.setStatus(PaymentStatus.CANCELED);
        paymentRepo.save(payment);
    }

    public PaymentResponse getPayment(UUID uuid) throws ResourceNotFoundException {
        return new PaymentResponse(
                paymentRepo
                        .findByPaymentUid(uuid)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Payment with " + uuid + "not founded")
                        )
        );
    }
}
