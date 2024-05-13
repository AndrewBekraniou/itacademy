package com.itacademy.web_rental_car.controller;

import com.itacademy.web_rental_car.dto.CarDto;
import com.itacademy.web_rental_car.model.domain.Car;
import com.itacademy.web_rental_car.model.domain.CarData;
import com.itacademy.web_rental_car.service.CarDataService;
import com.itacademy.web_rental_car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final CarDataService carDataService;

    @GetMapping("/showAllCars")
    @ResponseBody
    public List<CarDto> showAllCars() {
        return carService.getAllCarsWithDetails();
    }

    @GetMapping("/carDetails")
    public String carDetails(Model model, @RequestParam Integer id) {
        CarData carData = carDataService.getCarDataById(id);
        model.addAttribute("carData", carData);
        return "carDetails";
    }

    @GetMapping("/mainPage")
    public String carMain(Model model) {
        int numberOfCarsToShow = 12;
        return carService.addRandomCarsToModel(model, numberOfCarsToShow);
    }

    @GetMapping("/addCar")
    public String showCarForm(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("carData", new CarData());
        return "addCar";
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute("car") Car car, @ModelAttribute("carData") CarData carData) {
        carService.addCarWithCarData(car, carData);
        return "redirect:/adminPage";
    }

    @DeleteMapping("/delete/{regNumber}")
    public ResponseEntity<String> deleteCar(@PathVariable("regNumber") String regNumber) {
        carService.deleteCarByRegNumber(regNumber);
        return ResponseEntity.ok().body("Car and its data deleted successfully!");
    }
}











