package com.itacademy.web_rental_car.dto.mapper;


import com.itacademy.web_rental_car.dto.OrderDto;
import com.itacademy.web_rental_car.model.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto orderToOrderDto(Order order);
}