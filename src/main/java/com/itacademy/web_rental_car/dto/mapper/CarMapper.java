package com.itacademy.web_rental_car.dto.mapper;

import com.itacademy.web_rental_car.dto.CarDto;
import com.itacademy.web_rental_car.model.domain.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);


    CarDto carToCarDto(Car car);

}