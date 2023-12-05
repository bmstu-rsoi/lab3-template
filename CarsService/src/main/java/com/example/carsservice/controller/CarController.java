package com.example.carsservice.controller;

import com.example.carsservice.dto.response.CarPriceResponse;
import com.example.carsservice.dto.response.CarResponse;
import com.example.carsservice.dto.response.GetCarsResponse;
import com.example.carsservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping("/cars")
    public GetCarsResponse getCars(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "showAll", defaultValue="false") Boolean showAll) {
        return carService.getCars(page, size, showAll);
    }

    @GetMapping("/cars/{id}/price")
    public ResponseEntity<?> getCarPrice(@PathVariable(name = "id") UUID carUid) {
        Integer price = carService.getCarPrice(carUid);
        if (price != null) {
            System.out.println(price);
            return ResponseEntity.ok(new CarPriceResponse(price));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<?> getCar(@PathVariable(name = "id") UUID carUid) {
        CarResponse car = carService.getCar(carUid);
        if (car != null) {
            return ResponseEntity.ok(car);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{carUid}")
    public ResponseEntity<?> updateCarStatusByCarUid(@PathVariable UUID carUid) {
        carService.updateCarStatusByCarUid(carUid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
