package com.itacademy.web_rental_car;

import com.itacademy.web_rental_car.model.domain.Car;
import com.itacademy.web_rental_car.model.repository.CarRepository;
import com.itacademy.web_rental_car.service.impl.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarServiceTest {
    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Test
    public void testGetCarById_WhenCarExists() {
        Integer carId = 1;
        Car expectedCar = new Car();
        expectedCar.setId(carId);
        when(carRepository.findById(carId)).thenReturn(Optional.of(expectedCar));
        Car actualCar = carService.getCarById(carId);
        assertEquals(expectedCar, actualCar);
    }

    @Test
    public void testGetCarById_WhenCarDoesNotExist() {
        Integer carId = 1;
        when(carRepository.findById(carId)).thenReturn(Optional.empty());
        Car actualCar = carService.getCarById(carId);
        assertEquals(null, actualCar);
    }

    @Test
    public void testGetAllCarIds() {
        List<Integer> expectedCarIds = Arrays.asList(1, 2, 3);
        when(carRepository.findAllCarIds()).thenReturn(expectedCarIds);
        List<Integer> actualCarIds = carService.getAllCarIds();
        assertEquals(expectedCarIds, actualCarIds);
    }

    @Test
    public void testGetAllCars() {
        List<Car> expectedCars = new ArrayList<>();
        expectedCars.add(new Car());
        expectedCars.add(new Car());
        expectedCars.add(new Car());
        when(carRepository.findAll()).thenReturn(expectedCars);
        List<Car> actualCars = carService.getAllCars();
        assertEquals(expectedCars, actualCars);
    }
}
