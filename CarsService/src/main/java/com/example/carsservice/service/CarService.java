package com.example.carsservice.service;

import com.example.carsservice.dao.Car;
import com.example.carsservice.dto.response.CarResponse;
import com.example.carsservice.dto.response.GetCarsResponse;
import com.example.carsservice.exceptin.ResourceNotFoundException;
import com.example.carsservice.repo.CarRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepo carRepo;

    public CarService(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    public GetCarsResponse getCars(Integer page, Integer size, Boolean showAll) {
        List<Car> cars = carRepo.findCarsWithPagination((page - 1) * size, size, !showAll);
        long count = carRepo.count();
        return new GetCarsResponse(
                page,
                size,
                count,
                cars.stream().map(CarResponse::new).collect(Collectors.toList())
        );
    }

    public Integer getCarPrice(UUID carUid) {
        return carRepo
                .findByUuid(carUid)
                .map(Car::getPrice)
                .orElse(null);
    }

    public CarResponse getCar(UUID carUid) {
        return carRepo
                .findByUuid(carUid)
                .map(CarResponse::new)
                .orElse(null);
    }

    public void updateCarStatusByCarUid(UUID carUid) {
        Car car = carRepo.findByUuid(carUid).orElseThrow(() -> new ResourceNotFoundException(String.format("Could " +
                "not find car with carUid %s", carUid.toString())));
        car.setAvailability(!car.getAvailability());
        carRepo.save(car);
    }
}
