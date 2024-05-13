package com.itacademy.web_rental_car.service.impl;

import com.itacademy.web_rental_car.dto.CarDto;
import com.itacademy.web_rental_car.model.domain.Car;
import com.itacademy.web_rental_car.model.domain.CarData;
import com.itacademy.web_rental_car.model.repository.CarDataRepository;
import com.itacademy.web_rental_car.model.repository.CarRepository;
import com.itacademy.web_rental_car.service.CarDataService;
import com.itacademy.web_rental_car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;


@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarDataService carDataService;
    private final CarDataRepository carDataRepository;

    @Override
    public Car getCarById(Integer id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        return optionalCar.orElse(null);
    }

    @Override
    public List<Integer> getAllCarIds() {
        return carRepository.findAllCarIds();
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public List<CarDto> getAllCarsWithDetails() {
        List<Car> allCars = getAllCars();
        List<CarData> allCarData = carDataService.getAllCarData();
        List<CarDto> carDtoList = new ArrayList<>();
        for (Car car : allCars) {
            CarDto carDto = new CarDto();
            carDto.setId(car.getId());
            carDto.setRegNumber(car.getRegNumber());
            carDto.setAvailable(car.getAvailable());
            for (CarData carData : allCarData) {
                if (carData.getCar().getId().equals(car.getId())) {
                    carDto.setManufacturer(carData.getManufacturer());
                    carDto.setModel(carData.getModel());
                    break;
                }
            }
            carDtoList.add(carDto);
        }
        return carDtoList;
    }

    @Override
    public String addRandomCarsToModel(Model model, int numberOfCarsToShow) {
        List<Integer> availableIds = getAllCarIds();
        if (availableIds.isEmpty()) {
            return "errorPage";
        }
        Random random = new Random();
        Set<Integer> chosenIndices = new HashSet<>();
        while (chosenIndices.size() < numberOfCarsToShow) {
            int randomIndex = random.nextInt(availableIds.size());
            chosenIndices.add(randomIndex);
        }
        List<Car> randomCars = new ArrayList<>();
        for (Integer index : chosenIndices) {
            int randomId = availableIds.get(index);
            Car randomCar = getCarById(randomId);
            randomCars.add(randomCar);
        }
        for (int i = 0; i < randomCars.size(); i++) {
            model.addAttribute("car" + (i + 1), randomCars.get(i));
            Optional<CarData> carDataOptional = Optional.ofNullable(carDataService.getCarDataById(randomCars.get(i).getId()));
            String manufacturer = carDataOptional.map(CarData::getManufacturer).orElse("Manufacturer data not found");
            String modelCar = carDataOptional.map(CarData::getModel).orElse("Model not found");
            model.addAttribute("manufacturer" + (i + 1), manufacturer);
            model.addAttribute("model" + (i + 1), modelCar);
        }
        model.addAttribute("allCars", randomCars);
        return "mainPage";
    }

    @Override
    public void addCarWithCarData(Car car, CarData carData) {
        Car existingCar = carRepository.findByRegNumber(car.getRegNumber());
        if (existingCar == null) {
            car.setCarData(carData);
            carData.setCar(car);
            carRepository.save(car);
            carDataRepository.save(carData);
        } else {
            throw new IllegalArgumentException("Car with the same registration number already exists!");
        }
    }
    @Override
    public void deleteCarByRegNumber(String regNumber) {
        Car car = carRepository.findByRegNumber(regNumber);
        if (car != null) {
            carDataRepository.delete(car.getCarData());
            carRepository.delete(car);
        }
    }


}
