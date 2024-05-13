package com.itacademy.web_rental_car.controller;

import com.itacademy.web_rental_car.model.domain.CarData;
import com.itacademy.web_rental_car.service.CarDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CarDataController {
    private final CarDataService carDataService;

    @GetMapping("/carDetails/{id}")
    public String getCarDetailsById(@PathVariable Integer id, Model model) {
        Optional<CarData> carDataOptional = Optional.ofNullable(carDataService.getCarDataById(id));
        if (carDataOptional.isPresent()) {
            CarData carData = carDataOptional.get();
            model.addAttribute("carData", carData);
        }
        return "carDetails";
    }
}





