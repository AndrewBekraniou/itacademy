package com.itacademy.web_rental_car.dto.mapper;

import com.itacademy.web_rental_car.dto.DamageDto;
import com.itacademy.web_rental_car.model.domain.Damage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DamageMapper {
    DamageMapper INSTANCE = Mappers.getMapper(DamageMapper.class);

    DamageDto damageToDamageDto(Damage damage);

    Damage damageDtoToDamage(DamageDto damageDto);
}