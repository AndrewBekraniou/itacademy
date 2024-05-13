package com.itacademy.web_rental_car.dto;

import lombok.Data;

@Data
public class DamageDto {
    private Integer id;
    private String infoDetailsDamage;
    private double paymentForDamage;
    private Integer orderId;
}