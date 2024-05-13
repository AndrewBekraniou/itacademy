package com.itacademy.web_rental_car.dto;

import lombok.Data;

@Data
public class CarDto {

    private Integer id;
    private String regNumber;
    private String available;
    private String manufacturer;
    private String model;
}
