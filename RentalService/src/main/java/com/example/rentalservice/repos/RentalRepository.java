package com.example.rentalservice.repos;

import com.example.rentalservice.dao.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    List<Rental> findAllByUsername(String username);
    Optional<Rental> findByRentalUidAndUsername(UUID rentalUid, String username);
}
