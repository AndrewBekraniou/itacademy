package com.itacademy.web_rental_car;

import com.itacademy.web_rental_car.model.domain.*;
import com.itacademy.web_rental_car.model.domain.enums.OrderStatus;
import com.itacademy.web_rental_car.model.repository.OrderRepository;
import com.itacademy.web_rental_car.service.CarDataService;
import com.itacademy.web_rental_car.service.CarService;
import com.itacademy.web_rental_car.service.PassportDataService;
import com.itacademy.web_rental_car.service.UserService;
import com.itacademy.web_rental_car.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CarDataService carDataService;
    @Mock
    private PassportDataService passportDataService;
    @Mock
    private CarService carService;
    @Mock
    private UserService userService;
    @Mock
    private Principal principal;

    @Test
    public void testGetOrderById() {
        Integer orderId = 1;
        Order expectedOrder = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(expectedOrder));
        Order actualOrder = orderService.getOrderById(orderId);
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void testSaveOrder() {
        Order order = new Order();
        orderService.saveOrder(order);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testGetAllOrders() {
        List<Order> expectedOrders = Collections.singletonList(new Order());
        when(orderRepository.findAll()).thenReturn(expectedOrders);
        List<Order> actualOrders = orderService.getAllOrders();
        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void testGetAllOrdersForUser() {
        Integer userId = 1;
        List<Order> expectedOrders = Collections.singletonList(new Order());
        when(orderRepository.findByUserId(userId)).thenReturn(expectedOrders);
        List<Order> actualOrders = orderService.getAllOrdersForUser(userId);
        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void testSetCarManufacturerAndModel() {
        Order order = new Order();
        CarData carData = new CarData();
        carData.setManufacturer("Toyota");
        carData.setModel("Camry");
        Car car = new Car();
        car.setCarData(carData);
        order.setCar(car);
        when(carDataService.getCarDataById(anyInt())).thenReturn(carData);
        orderService.setCarManufacturerAndModel(order, "Toyota", "Camry");
        assertEquals("Toyota", order.getCar().getCarData().getManufacturer());
        assertEquals("Camry", order.getCar().getCarData().getModel());
    }

    @Test
    public void testSetNameAndSurname() {
        Order order = new Order();
        PassportData passportData = new PassportData();
        passportData.setName("Ivan");
        passportData.setSurname("Ivanov");
        User user = new User();
        user.setPassportData(passportData);
        order.setUser(user);
        when(passportDataService.getPassportDataByUserId(anyInt())).thenReturn(passportData);
        orderService.setNameAndSurname(order, "Ivan", "Ivanov");
        assertEquals("Ivan", order.getUser().getPassportData().getName());
        assertEquals("Ivanov", order.getUser().getPassportData().getSurname());
    }

    @Test
    public void testCreateOrder() {
        Integer carId = 1;
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(System.currentTimeMillis() + 86400000);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        Car car = new Car();
        car.setId(carId);
        car.setCarData(new CarData());
        User user = new User();
        user.setUsername("Ivan");
        when(principal.getName()).thenReturn("Ivan");
        when(carService.getCarById(carId)).thenReturn(car);
        when(userService.getUserByUsername("Ivan")).thenReturn(user);
        when(carDataService.getRentPricePerDayByCarId(carId)).thenReturn(100.0);
        String result = orderService.createOrder(carId, startDate, endDate, principal, redirectAttributes);
        assertEquals("redirect:/orders/placingOrder", result);
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    public void testCalculateTotalPrice() {
        Date startDate = Date.valueOf("2024-05-10");
        Date endDate = Date.valueOf("2024-05-12");
        double rentPricePerDay = 100.0;
        double totalPrice = orderService.calculateTotalPrice(startDate, endDate, rentPricePerDay);
        assertEquals(200.0, totalPrice);
    }

    @Test
    public void testPayOrderWithModelAndSession() {
        Integer orderId = 1;
        Order order = new Order();
        order.setOrderStatus(OrderStatus.CREATED);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        String result = orderService.payOrder(orderId, model, session);
        assertEquals("redirect:/userPage", result);
        assertEquals(OrderStatus.PAID, order.getOrderStatus());
        verify(orderRepository, times(1)).save(order);
    }

}




