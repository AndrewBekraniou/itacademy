package com.itacademy.web_rental_car.dto.mapper;

import com.itacademy.web_rental_car.dto.CarDataDto;
import com.itacademy.web_rental_car.model.domain.CarData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarDataMapper {
    CarDataMapper INSTANCE = Mappers.getMapper(CarDataMapper.class);

    CarDataDto carDataToCarDataDto(CarData carData);
}