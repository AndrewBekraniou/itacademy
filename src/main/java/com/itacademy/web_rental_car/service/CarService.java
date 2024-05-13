package com.itacademy.web_rental_car.service;

import com.itacademy.web_rental_car.dto.CarDto;
import com.itacademy.web_rental_car.model.domain.Car;
import com.itacademy.web_rental_car.model.domain.CarData;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public interface CarService {

    List<Integer> getAllCarIds();

    Car getCarById(Integer carId);

    List<Car> getAllCars();

    List<CarDto> getAllCarsWithDetails();

    String addRandomCarsToModel(Model model, int numberOfCars);

    void addCarWithCarData(Car car, CarData carData);


    void deleteCarByRegNumber(String regNumber);
}