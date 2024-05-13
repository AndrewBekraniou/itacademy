package com.itacademy.web_rental_car.controller;

import com.itacademy.web_rental_car.model.domain.PassportData;
import com.itacademy.web_rental_car.service.PassportDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PassportDataController {

    private final PassportDataService passportDataService;

    @PostMapping("/carDetails/confirm")
    @ResponseBody
    public String saveOrUpdatePassportData(@RequestBody PassportData passportData, Principal principal) {
        return passportDataService.saveOrUpdatePassportData(passportData, principal);
    }
}