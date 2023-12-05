package com.example.gatewayservice.controller;

import com.example.gatewayservice.dto.req.RentalRequest;
import com.example.gatewayservice.dto.resp.AnswerResp;
import com.example.gatewayservice.dto.resp.GetRentalResponse;
import com.example.gatewayservice.feign_service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/rental", produces = MediaType.APPLICATION_JSON_VALUE)
public class RentalController {
    @Autowired
    private final RentalService rentalService;
    @Autowired
    private final CarController controller;
    private final String X_User_Name_HEADER = "X-User-Name";
    private final TaskScheduler scheduler;

    private static final Integer N = 10;
    private Integer errorsNumber = 0;
    private final Runnable healthCheck =
            new Runnable() {
                @Override
                public void run() {
                    try {
                        rentalService.ping();
                        System.out.println("Car healthCheck passed");
                        errorsNumber = 0;
                    } catch (Exception e) {
                        System.out.println("Car healthCheck for errors: " + errorsNumber);
                        scheduler.schedule(this, new Date(System.currentTimeMillis() + 10000L));
                    }
                }
            };


    public RentalController(RentalService rentalService, CarController controller, TaskScheduler scheduler) {
        this.rentalService = rentalService;
        this.controller = controller;
        this.scheduler = scheduler;
    }


    @GetMapping("")
    public ResponseEntity<?> getAllRental(@RequestHeader(X_User_Name_HEADER) String userName) {
        List<GetRentalResponse> result;
        try {
            if (errorsNumber >= N) {
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Rental Service unavailable"));
            } else {
                result = rentalService.getAllRental(userName);
                if (result != null) errorsNumber = 0;
            }
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Rental Service unavailable"));
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<?> createRental(@RequestHeader(X_User_Name_HEADER) String userName,
                                          @RequestBody RentalRequest createRentalRequestDto) {
        Object result;
        try {
            result = rentalService.createRental(userName, createRentalRequestDto);
        } catch (Exception e) {
            errorsNumber += 1;
            controller.updateCarStatusByCarUid(createRentalRequestDto.getCarUid());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Payment Service unavailable"));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{rentalUid}")
    public ResponseEntity<?> getRentalByUid(@RequestHeader(X_User_Name_HEADER) String userName,
                                            @PathVariable UUID rentalUid) {
        ResponseEntity result;
        try {
            if (errorsNumber >= N) {
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Rental Service unavailable"));
            } else {
                result = rentalService.getRentalByUid(userName, rentalUid);
                System.out.println(result);
                if (result != null) errorsNumber = 0;
            }
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Rental Service unavailable"));
        }
        return result;
    }

    @DeleteMapping("/{rentalUid}")
    public ResponseEntity<?> removeRentalByUid(@RequestHeader(X_User_Name_HEADER) String userName,
                                               @PathVariable UUID rentalUid) {
        try {
            rentalService.cancelRentalByUid(userName, rentalUid);
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Rental Service unavailable"));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{rentalUid}/finish")
    public ResponseEntity<?> finishRentalByUid(@RequestHeader(X_User_Name_HEADER) String userName,
                                               @PathVariable UUID rentalUid) {
        try {
            rentalService.finishRentalByUid(userName, rentalUid);
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerResp("Rental Service unavailable"));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
