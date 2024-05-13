package com.itacademy.web_rental_car.model.repository;

import com.itacademy.web_rental_car.model.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("SELECT c.id FROM Car c")
    List<Integer> findAllCarIds();

    Optional<Car> findById(Integer carId);
    Car findByRegNumber(String regNumber);

}
