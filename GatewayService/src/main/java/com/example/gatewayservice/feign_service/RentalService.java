package com.example.gatewayservice.feign_service;

import com.example.gatewayservice.dto.req.RentalRequest;
import com.example.gatewayservice.dto.resp.GetRentalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "rental-service", url = "http://rental:8060")
public interface RentalService {
    @GetMapping("/manage/health")
    String ping();

    @GetMapping("/api/v1/rental")
    List<GetRentalResponse> getAllRental(@RequestHeader("X-User-Name") String username);

    @PostMapping("/api/v1/rental")
    Object createRental(@RequestHeader("X-User-Name") String username,
                                         @RequestBody RentalRequest createRentalRequestDto);

    @GetMapping("/api/v1/rental/{rentalUid}")
    ResponseEntity<?> getRentalByUid(@RequestHeader("X-User-Name") String username,
                                     @PathVariable UUID rentalUid);

    @DeleteMapping("/api/v1/rental/{rentalUid}")
    ResponseEntity<String> cancelRentalByUid(@RequestHeader("X-User-Name") String username,
                                             @PathVariable UUID rentalUid);

    @PostMapping("/api/v1/rental/{rentalUid}/finish")
    ResponseEntity<String> finishRentalByUid(@RequestHeader("X-User-Name") String username,
                                             @PathVariable UUID rentalUid);
}
