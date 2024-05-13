package com.itacademy.web_rental_car;

import com.itacademy.web_rental_car.model.domain.Car;
import com.itacademy.web_rental_car.model.domain.CarData;
import com.itacademy.web_rental_car.model.repository.CarDataRepository;
import com.itacademy.web_rental_car.model.repository.CarRepository;
import com.itacademy.web_rental_car.service.impl.CarDataServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CarDataServiceTest {
    @Mock
    private CarDataRepository carDataRepository;
    @Mock
    private CarRepository carRepository;
    @InjectMocks
    private CarDataServiceImpl carDataService;

    @Test
    void testGetCarDataById() {
        int carId = 2;
        CarData carData = new CarData();
        carData.setId(carId);
        when(carDataRepository.findById(carId)).thenReturn(Optional.of(carData));
        CarData result = carDataService.getCarDataById(carId);
        assertNotNull(result);
        assertEquals(carId, result.getId());
    }

    @Test
    void testGetAllCarData() {
        List<CarData> carDataList = Collections.singletonList(new CarData());
        when(carDataRepository.findAll()).thenReturn(carDataList);
        List<CarData> result = carDataService.getAllCarData();
        assertNotNull(result);
        assertEquals(carDataList.size(), result.size());
    }

    @Test
    void testSaveCarData() {
        CarData carData = new CarData();
        carData.setId(1);
        when(carDataRepository.save(carData)).thenReturn(carData);
        CarData savedCarData = carDataService.saveCarData(carData);
        assertNotNull(savedCarData);
        assertEquals(carData.getId(), savedCarData.getId());
    }

    @Test
    void testGetRentPricePerDayByCarId() {
        int carId = 2;
        Car car = new Car();
        car.setId(carId);
        CarData carData = new CarData();
        carData.setId(carId);
        carData.setRentPricePerDay(100.0);
        car.setCarData(carData);
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Double rentPrice = carDataService.getRentPricePerDayByCarId(carId);
        assertNotNull(rentPrice);
        assertEquals(carData.getRentPricePerDay(), rentPrice);
    }

}