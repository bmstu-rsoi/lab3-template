package com.example.carsservice.service;

import com.example.carsservice.dao.Car;
import com.example.carsservice.dto.response.CarResponse;
import com.example.carsservice.dto.response.GetCarsResponse;
import com.example.carsservice.repo.CarRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CarServiceTest {
    @Mock
    private CarRepo carRepo;
    private CarService carService;
    private Car car;
    private UUID uuid = UUID.fromString("675e9b8d-9ed9-4271-a72f-1d44f1f0c8a1");

    @BeforeEach
    public void initEach() {
        carService = new CarService(carRepo);
        car = new Car();
        car.setBrand("MAZDA");
        car.setAvailability(true);
        car.setPrice(1000);
        car.setUuid(uuid);
    }

    @Test
    void getCarsShouldReturnAllCars() {
        when(carRepo.findCarsWithPagination(0, 10, false)).thenReturn(Arrays.asList(car));

        GetCarsResponse carsResponse = carService.getCars(1, 10, true);

        assertAll("Verify car properties",
                () -> assertEquals(car.getAvailability(), carsResponse.getItems().get(0).getAvailable()),
                () -> assertEquals(car.getBrand(), carsResponse.getItems().get(0).getBrand()),
                () -> assertEquals(1, carsResponse.getPage()),
                () -> assertEquals(10, carsResponse.getPageSize()));
        verify(carRepo, times(1)).findCarsWithPagination(0, 10, false);
    }

    @Test
    void getCarPriceShouldReturnCarPrice() {
        when(carRepo.findByUuid(uuid)).thenReturn(java.util.Optional.ofNullable(car));

        Integer carPrice = carService.getCarPrice(uuid);

        verify(carRepo, times(1)).findByUuid(uuid);
        assertEquals(carPrice, car.getPrice());
    }

    @Test
    void getCarShouldReturnCar() {
        when(carRepo.findByUuid(uuid)).thenReturn(java.util.Optional.ofNullable(car));

        CarResponse carResponse = carService.getCar(uuid);

        verify(carRepo, times(1)).findByUuid(uuid);
        assertEquals(carResponse.getBrand(), car.getBrand());
    }
}