package com.itacademy.web_rental_car.dto;

import com.itacademy.web_rental_car.model.domain.enums.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String phone;
    private List<UserRole> roles;

}
