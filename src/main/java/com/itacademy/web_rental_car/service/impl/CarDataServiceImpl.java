package com.itacademy.web_rental_car.service.impl;

import com.itacademy.web_rental_car.model.domain.Car;
import com.itacademy.web_rental_car.model.domain.CarData;
import com.itacademy.web_rental_car.model.repository.CarDataRepository;
import com.itacademy.web_rental_car.model.repository.CarRepository;
import com.itacademy.web_rental_car.service.CarDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarDataServiceImpl implements CarDataService {

    private final CarDataRepository carDataRepository;
    private final CarRepository carRepository;

    @Override
    public CarData getCarDataById(Integer carId) {
        return carDataRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("CarData not found for id: " + carId));
    }
    @Override
    public List<CarData> getAllCarData() {
        List<CarData> carDataList = carDataRepository.findAll();
        if (carDataList.isEmpty()) {
            throw new RuntimeException("No CarData found");
        }
        return carDataList;
    }
    @Override
    public CarData saveCarData(CarData carData) {
        return carDataRepository.save(carData);
    }
    @Override
    public Double getRentPricePerDayByCarId(Integer carId) {
        try {
            Car car = carRepository.findById(carId).orElseThrow(() ->
                    new RuntimeException("Car not found for id: " + carId));
            return car.getCarData().getRentPricePerDay();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


