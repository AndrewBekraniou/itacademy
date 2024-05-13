package com.itacademy.web_rental_car.service.impl;

import com.itacademy.web_rental_car.model.domain.PassportData;
import com.itacademy.web_rental_car.model.domain.User;
import com.itacademy.web_rental_car.model.repository.PassportDataRepository;
import com.itacademy.web_rental_car.service.PassportDataService;
import com.itacademy.web_rental_car.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class PassportDataServiceImpl implements PassportDataService {

    private final PassportDataRepository passportDataRepository;
    private final UserService userService;
    @Override
    public PassportData savePassportData(PassportData passportData) {
        return passportDataRepository.save(passportData);
    }

    @Override
    public PassportData getPassportDataByUserId(Integer userId) {
        return passportDataRepository.findByUserId(userId);
    }

    @Override
    public PassportData findByUser(User user) {
        return passportDataRepository.findByUser(user);
    }

    @Override
    public boolean existsByPassportNumberForOtherUsers(String passportNumber, User user) {
        return passportDataRepository.existsByPassportNumberAndUserNot(passportNumber, user);
    }

    @Override
    public boolean existsByIdentificationNumberForOtherUsers(String identificationNumber, User user) {
        return passportDataRepository.existsByIdentificationNumberAndUserNot(identificationNumber, user);
    }

    @Override
    public String saveOrUpdatePassportData(PassportData passportData, Principal principal) {
        if (principal == null) {
            return "Please log in";
        } else {
            String username = principal.getName();
            User user = userService.getByUsername(username);
            if (user == null) {
                return "User not found";
            } else {
                passportData.setUser(user);
                boolean passportExistsForOtherUsers = existsByPassportNumberForOtherUsers(passportData.getPassportNumber(), user);
                boolean identificationExistsForOtherUsers = existsByIdentificationNumberForOtherUsers(passportData.getIdentificationNumber(), user);
                if (passportExistsForOtherUsers || identificationExistsForOtherUsers) {
                    return "Data already exists for other users";
                } else {
                    PassportData existingPassportData = findByUser(user);
                    if (existingPassportData != null) {
                        existingPassportData.setName(passportData.getName());
                        existingPassportData.setSurname(passportData.getSurname());
                        existingPassportData.setPassportNumber(passportData.getPassportNumber());
                        existingPassportData.setIdentificationNumber(passportData.getIdentificationNumber());
                        existingPassportData.setBirthDate(passportData.getBirthDate());
                        existingPassportData.setGender(passportData.getGender());
                        existingPassportData.setAddress(passportData.getAddress());
                        savePassportData(existingPassportData);
                        return "Data updated successfully";
                    } else {
                        return "Passport data not found for the user";
                    }
                }
            }
        }
    }
}