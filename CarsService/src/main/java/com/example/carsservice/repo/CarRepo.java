package com.example.carsservice.repo;

import com.example.carsservice.dao.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepo extends JpaRepository<Car, Integer> {
    List<Car> findAllByAvailability(Boolean availability);

    Optional<Car> findByUuid(UUID uuid);

    @Query(value = "SELECT * from cars.public.cars where availability = :availability order by id OFFSET :offset LIMIT :pageSize", nativeQuery = true)
    List<Car> findCarsWithPagination(Integer offset, Integer pageSize, Boolean availability);
}
