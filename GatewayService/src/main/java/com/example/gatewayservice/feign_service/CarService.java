package com.example.gatewayservice.feign_service;

import com.example.gatewayservice.dto.resp.CarResponse;
import com.example.gatewayservice.dto.resp.GetCarsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "car-service", url = "http://car:8070")
public interface CarService {
    @GetMapping("/manage/health")
    String ping();

    @GetMapping("/api/v1/cars")
    GetCarsResponse getAllAvailabilityCar(@RequestParam(name = "page") Integer page,
                                          @RequestParam(name = "size") Integer size,
                                          @RequestParam(name = "showAll") boolean showAll);
    @GetMapping("/api/v1/cars/{carUid}")
    CarResponse getCarByCarUid(@PathVariable UUID carUid);
    @PostMapping("/api/v1/cars/{carUid}")
    ResponseEntity<?> updateCarStatusByCarUid(@PathVariable UUID carUid);

    @GetMapping("api/v1/cars/{id}/price")
    ResponseEntity<?> getCarPrice(@PathVariable(name = "id") UUID carUid);
}