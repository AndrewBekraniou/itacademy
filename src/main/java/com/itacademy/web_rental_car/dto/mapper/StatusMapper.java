package com.itacademy.web_rental_car.dto.mapper;

import com.itacademy.web_rental_car.dto.StatusDto;
import com.itacademy.web_rental_car.model.domain.Status;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StatusMapper {
    StatusMapper INSTANCE = Mappers.getMapper(StatusMapper.class);

    StatusDto statusToStatusDto(Status status);
}