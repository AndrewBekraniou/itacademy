package com.itacademy.web_rental_car.service;

import com.itacademy.web_rental_car.model.domain.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Date;
import java.util.List;


public interface OrderService {

    List<Order> getAllOrders();

    Order getOrderById(Integer id);

    void saveOrder(Order order);

    void payOrder(Integer orderId);

    double calculateTotalPrice(java.sql.Date startDate, java.sql.Date endDate, double rentPricePerDay);

    List<Order> getAllOrdersForUser(Integer userId);

    void setCarManufacturerAndModel(Order order, String manufacturer, String model);

    void setNameAndSurname(Order order, String name, String surname);

    void updateOrderStatus(Integer orderId, String status);

    String createOrder(Integer carId, Date startDate, Date endDate, Principal principal, RedirectAttributes redirectAttributes);
    List<Order> getAllOrdersWithDetails();

    String payOrder(Integer orderId, Model model, HttpSession session);
    String placingOrder(Model model);

}