package com.example.rentalservice.controllers;

import com.example.rentalservice.dto.response.GetRentalResponse;
import com.example.rentalservice.enums.RentalStatus;
import com.example.rentalservice.exception.ResourceNotFoundException;
import com.example.rentalservice.dto.request.RentalRequest;
import com.example.rentalservice.dto.response.ResourceNotFoundResponse;
import com.example.rentalservice.services.RentalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Validated
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @GetMapping("/rental")
    public List<GetRentalResponse> getUserRentals(@RequestHeader(name = "X-User-Name") String username) throws URISyntaxException, IOException, InterruptedException {
        return rentalService.getUserRentals(username);
    }

    @GetMapping("/rental/{rentalUid}")
    public ResponseEntity<?> getRental(
            @PathVariable(name = "rentalUid") UUID rentalUid,
            @RequestHeader(name = "X-User-Name") String username) throws URISyntaxException, IOException, InterruptedException {
        try {
            return ResponseEntity.ok(rentalService.getRental(rentalUid, username));
        }
        catch (ResourceNotFoundException objectNotFoundException) {
            return new ResponseEntity<>(new ResourceNotFoundResponse(objectNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/rental")
    public ResponseEntity<?> createRental(
            @RequestHeader(name = "X-User-Name") String username,
            @Valid @RequestBody RentalRequest rentalRequest
    ) throws URISyntaxException, IOException, InterruptedException {
        return ResponseEntity.ok(rentalService.createRental(username, rentalRequest));
    }

    @DeleteMapping("/rental/{rentalUid}")
    public ResponseEntity<?> cancelRental(
            @PathVariable(name = "rentalUid") UUID rentalUid,
            @RequestHeader(name = "X-User-Name") String username
    ) throws URISyntaxException, IOException, InterruptedException {
        try {
            rentalService.changeRentalStatus(rentalUid, username, RentalStatus.CANCELED);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResourceNotFoundResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/rental/{rentalUid}/finish")
    public ResponseEntity<?> finishRental(
            @PathVariable(name = "rentalUid") UUID rentalUid,
            @RequestHeader(name = "X-User-Name") String username
    ) throws URISyntaxException, IOException, InterruptedException {
        try {
            rentalService.changeRentalStatus(rentalUid, username, RentalStatus.FINISHED);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResourceNotFoundResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
