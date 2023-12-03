package com.example.paymentservice;

import com.example.paymentservice.dao.Payment;
import com.example.paymentservice.dto.request.PaymentRequest;
import com.example.paymentservice.dto.response.PaymentResponse;
import com.example.paymentservice.enums.PaymentStatus;
import com.example.paymentservice.exception.ResourceNotFoundException;
import com.example.paymentservice.repos.PaymentRepo;
import com.example.paymentservice.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PaymentServiceApplicationTests {
    @Mock
    private PaymentRepo paymentRepo;
    private PaymentService paymentService;
    private Payment payment;
    private UUID uuid = UUID.fromString("675e9b8d-9ed9-4271-a72f-1d44f1f0c8a1");

    @BeforeEach
    public void initEach() {
        paymentService = new PaymentService(paymentRepo);
        payment = new Payment();
        payment.setPaymentUid(uuid);
        payment.setPrice(1000);
    }

    @Test
    void createPaymentShouldReturnCorrectResponse() {
        when(paymentRepo.save(any())).thenReturn(payment);

        PaymentResponse paymentResponse = paymentService.createPayment(new PaymentRequest(null, 1000));

        assertEquals(paymentResponse.getPrice(), 1000);
        verify(paymentRepo, times(1)).save(any());
    }

    @Test
    void cancelPaymentShouldChangeStatus() {
        when(paymentRepo.findByPaymentUid(uuid)).thenReturn(java.util.Optional.ofNullable(payment));

        try {
            paymentService.cancelPayment(uuid);

        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(payment.getStatus(), PaymentStatus.CANCELED);
    }
}
