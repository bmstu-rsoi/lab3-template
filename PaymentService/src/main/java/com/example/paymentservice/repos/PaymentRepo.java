package com.example.paymentservice.repos;

import com.example.paymentservice.dao.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer> {
    Optional<Payment> findByPaymentUid(UUID paymentUid);
}
