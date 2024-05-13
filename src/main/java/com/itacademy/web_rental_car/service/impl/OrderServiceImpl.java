package com.itacademy.web_rental_car.service.impl;

import com.itacademy.web_rental_car.model.domain.*;
import com.itacademy.web_rental_car.model.domain.enums.OrderStatus;
import com.itacademy.web_rental_car.model.repository.OrderRepository;
import com.itacademy.web_rental_car.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CarDataService carDataService;
    private final PassportDataService passportDataService;
    private final CarService carService;
    private final UserService userService;

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        order.setConfirmationDate(new Timestamp(System.currentTimeMillis()));
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Integer orderId, String status) {
        Order order = getOrderById(orderId);
        if (status.equals("CONFIRMED")) {
            order.setOrderStatus(OrderStatus.CONFIRMED);
        } else if (status.equals("REJECTED")) {
            order.setOrderStatus(OrderStatus.REJECTED);
        } else if (status.equals("CLOSED")) {
            order.setOrderStatus(OrderStatus.CLOSED);
        }
        saveOrder(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void payOrder(Integer orderId) {
    }

     @Override
    public List<Order> getAllOrdersForUser(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public void setCarManufacturerAndModel(Order order, String manufacturer, String model) {
        try {
            if (order != null) {
                Car car = order.getCar();
                if (car != null) {
                    CarData carData = carDataService.getCarDataById(car.getId());
                    if (carData != null) {
                        carData.setManufacturer(manufacturer);
                        carData.setModel(model);
                        carDataService.saveCarData(carData);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while installing the vehicle manufacturer and model: " + e.getMessage());
        }
    }

    @Override
    public void setNameAndSurname(Order order, String name, String surname) {
        try {
            if (order != null && order.getUser() != null) {
                PassportData passportData = passportDataService.getPassportDataByUserId(order.getUser().getId());
                if (passportData != null) {
                    passportData.setName(name);
                    passportData.setSurname(surname);
                    passportDataService.savePassportData(passportData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while setting the name and surname: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public String createOrder(Integer carId,
                              Date startDate,
                              Date endDate,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        Car car = carService.getCarById(carId);
        if (car == null) {
            return "redirect:/carNotFound";
        }
        String username = principal.getName();
        User user = userService.getUserByUsername(username);

        Order order = new Order();
        order.setOrderStartDate(startDate);
        order.setOrderEndDate(endDate);
        order.setCar(car);
        order.setOrderStatus(OrderStatus.CREATED);
        Double rentPricePerDay = carDataService.getRentPricePerDayByCarId(carId);
        if (rentPricePerDay == null) {
            return "redirect:/rentPriceNotFound";
        }
        double totalPrice = calculateTotalPrice(startDate, endDate, rentPricePerDay);
        order.setTotalPrice(totalPrice);
        order.setUser(user);

        saveOrder(order);
        redirectAttributes.addFlashAttribute("carData", car);
        redirectAttributes.addFlashAttribute("order", order);
        redirectAttributes.addFlashAttribute("message", "The order has been successfully created");
        return "redirect:/orders/placingOrder";
    }

    @Override
    public double calculateTotalPrice(java.sql.Date startDate, java.sql.Date endDate, double rentPricePerDay) {
        long differenceInMillis = endDate.getTime() - startDate.getTime();
        long differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24);
        return rentPricePerDay * differenceInDays;
    }

    @Override
    public List<Order> getAllOrdersWithDetails() {
        List<Order> allOrders = getAllOrders();
        List<Order> ordersWithDetails = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getCar() != null) {
                CarData carData = carDataService.getCarDataById(order.getCar().getId());
                setCarManufacturerAndModel(order, carData.getManufacturer(), carData.getModel());
            }
            if (order.getUser() != null) {
                PassportData passportData = passportDataService.getPassportDataByUserId(order.getUser().getId());
                if (passportData != null) {
                    setNameAndSurname(order, passportData.getName(), passportData.getSurname());
                }
            }
            ordersWithDetails.add(order);
        }
        return ordersWithDetails;
    }

    @Override
    @Transactional
    public String payOrder(Integer orderId, Model model, HttpSession session) {
        Order order = getOrderById(orderId);
        if (order == null) {
            model.addAttribute("error", "Order not found.");
            return "redirect:/userPage";
        }
        payOrder(orderId);
        order.setOrderStatus(OrderStatus.PAID);
        saveOrder(order);
        return "redirect:/userPage";
    }
    @Override
    public String placingOrder(Model model) {
        Order order = (Order) model.getAttribute("order");
        if (order != null && order.getCar() != null) {
            Integer savedCarId = order.getCar().getId();
            CarData carData = carDataService.getCarDataById(savedCarId);
            if (carData != null) {
                model.addAttribute("carData", carData);
            }
        }
        return "placingOrder";
    }

}



