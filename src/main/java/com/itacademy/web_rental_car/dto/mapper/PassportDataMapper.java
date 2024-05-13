package com.itacademy.web_rental_car.dto.mapper;

import com.itacademy.web_rental_car.dto.PassportDataDto;
import com.itacademy.web_rental_car.model.domain.PassportData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PassportDataMapper {
    PassportDataMapper INSTANCE = Mappers.getMapper(PassportDataMapper.class);

    PassportDataDto passportDataToPassportDataDto(PassportData passportData);
}