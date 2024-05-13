package com.itacademy.web_rental_car.service;

import com.itacademy.web_rental_car.model.domain.PassportData;
import com.itacademy.web_rental_car.model.domain.User;

import java.security.Principal;

public interface PassportDataService {
    PassportData savePassportData(PassportData passportData);

    PassportData getPassportDataByUserId(Integer userId);

    PassportData findByUser(User user);

    boolean existsByPassportNumberForOtherUsers(String passportNumber, User user);

    boolean existsByIdentificationNumberForOtherUsers(String identificationNumber, User user);

    String saveOrUpdatePassportData(PassportData passportData, Principal principal);
}
