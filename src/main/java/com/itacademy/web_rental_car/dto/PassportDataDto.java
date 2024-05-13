package com.itacademy.web_rental_car.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PassportDataDto {
    private Integer id;
    private String name;
    private String surname;
    private String passportNumber;
    private String identificationNumber;
    private LocalDate birthDate;
    private String gender;
    private String address;

}