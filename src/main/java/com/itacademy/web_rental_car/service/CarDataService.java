package com.itacademy.web_rental_car.service;

import com.itacademy.web_rental_car.model.domain.CarData;

import java.util.List;


public interface CarDataService {

    CarData saveCarData(CarData carData);

    Double getRentPricePerDayByCarId(Integer carId);

    CarData getCarDataById(Integer carId);

    List<CarData> getAllCarData();


}

