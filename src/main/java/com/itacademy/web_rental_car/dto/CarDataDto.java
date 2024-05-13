package com.itacademy.web_rental_car.dto;

import com.itacademy.web_rental_car.model.domain.enums.CarBodyType;
import com.itacademy.web_rental_car.model.domain.enums.CarFuelType;
import com.itacademy.web_rental_car.model.domain.enums.CarTransmission;
import lombok.Data;

@Data
public class CarDataDto {
    private Integer id;
    private String manufacturer;
    private String color;
    private String model;
    private Integer yearOfIssue;
    private double rentPricePerDay;
    private CarBodyType bodyType;
    private CarFuelType fuelType;
    private CarTransmission transmission;
}