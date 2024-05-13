package com.itacademy.web_rental_car.dto.mapper;

import com.itacademy.web_rental_car.dto.UserDto;
import com.itacademy.web_rental_car.model.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "user.roles", target = "roles")
    UserDto userToUserDto(User user);
}




