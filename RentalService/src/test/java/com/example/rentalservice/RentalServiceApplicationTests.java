package com.example.rentalservice;

import com.example.rentalservice.dao.Rental;
import com.example.rentalservice.dto.response.GetRentalResponse;
import com.example.rentalservice.enums.RentalStatus;
import com.example.rentalservice.exception.ResourceNotFoundException;
import com.example.rentalservice.repos.RentalRepository;
import com.example.rentalservice.services.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RentalServiceApplicationTests {
    @Mock
    private RentalRepository rentalRepository;
    private RentalService rentalService;
    private Rental rental;
    private UUID uuid = UUID.fromString("675e9b8d-9ed9-4271-a72f-1d44f1f0c8a1");

    @BeforeEach
    public void initEach() {
        rentalService = new RentalService(rentalRepository);
        rental = new Rental();
        rental.setRentalUid(uuid);
        rental.setStatus(RentalStatus.IN_PROGRESS);
        rental.setDateTo(LocalDate.now().atStartOfDay());
        rental.setDateFrom(LocalDate.now().atStartOfDay());
    }

    @Test
    void changeRentalStatusShouldChangeStatus() throws URISyntaxException, IOException, ResourceNotFoundException, InterruptedException {
        when(rentalRepository.findByRentalUidAndUsername(uuid, "ok")).thenReturn(java.util.Optional.ofNullable(rental));
        when(rentalRepository.save(any())).thenReturn(rental);

        rentalService.changeRentalStatus(uuid, "ok", RentalStatus.FINISHED);

        assertEquals(rental.getStatus(), RentalStatus.FINISHED);
    }

}
