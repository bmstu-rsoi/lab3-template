package com.example.gatewayservice.controller;

import com.example.gatewayservice.dto.resp.AnswerResp;
import com.example.gatewayservice.dto.resp.CarPriceResponse;
import com.example.gatewayservice.dto.resp.CarResponse;
import com.example.gatewayservice.dto.resp.GetCarsResponse;
import com.example.gatewayservice.feign_service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/cars", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {
    private final CarService carService;
    private final TaskScheduler scheduler;

    private static final Integer N = 10;
    private Integer errorsNumber = 0;

    public CarController(CarService carService, TaskScheduler scheduler) {
        this.carService = carService;
        this.scheduler = scheduler;
    }

    private final Runnable healthCheck =
            new Runnable() {
                @Override
                public void run() {
                    try {
                        carService.ping();
                        System.out.println("Car healthCheck passed");
                        errorsNumber = 0;
                    } catch (Exception e) {
                        System.out.println("Car healthCheck for errors: " + errorsNumber);
                        scheduler.schedule(this, new Date(System.currentTimeMillis() + 10000L));
                    }
                }
            };

    @GetMapping("")
    public ResponseEntity<?> getAllAvailabilityCar(@RequestParam(name = "page") Integer page,
                                                   @RequestParam(name = "size") Integer size,
                                                   @RequestParam(name = "showAll") boolean showAll) {
        GetCarsResponse result;
        try {
            if (errorsNumber >= N) {
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Car Service unavailable"));
            } else {
                result = carService.getAllAvailabilityCar(page, size, showAll);
                if (result != null) errorsNumber = 0;
            }
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Car Service unavailable"));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{carUid}")
    public ResponseEntity<?> getCarByCarUid(@PathVariable UUID carUid) {
        CarResponse result;
        try {
            if (errorsNumber >= N) {
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Car Service unavailable"));
            } else {
                result = carService.getCarByCarUid(carUid);
                if (result != null) errorsNumber = 0;
            }
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Car Service unavailable"));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/price")
    public ResponseEntity<?> getCarPrice(@PathVariable(name = "id") UUID carUid) {
        ResponseEntity result;
        try {
            if (errorsNumber >= N) {
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Car Service unavailable"));
            } else {
                result = carService.getCarPrice(carUid);
                if (result != null) errorsNumber = 0;
            }
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Car Service unavailable"));
        }
        return result;
    }

    @PostMapping("/{carUid}")
    public ResponseEntity<?> updateCarStatusByCarUid(@PathVariable UUID carUid) {
        try {
            System.out.println("Trying update car status " + carUid);
            carService.updateCarStatusByCarUid(carUid);
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Car Service unavailable"));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
