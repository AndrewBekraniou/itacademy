package com.itacademy.web_rental_car.dto;

import com.itacademy.web_rental_car.model.domain.enums.OrderStatus;
import lombok.Data;

@Data
public class StatusDto {
    private Integer id;
    private OrderStatus status;
}
