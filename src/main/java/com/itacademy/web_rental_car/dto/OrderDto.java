package com.itacademy.web_rental_car.dto;

import com.itacademy.web_rental_car.model.domain.enums.OrderStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data

public class OrderDto {

    private Integer id;
    private Timestamp confirmationDate;
    private Date orderStartDate;
    private Date orderEndDate;
    private OrderStatus orderStatus;
    private Double totalPrice;
}
