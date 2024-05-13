package com.itacademy.web_rental_car.controller;

import com.itacademy.web_rental_car.model.domain.Order;
import com.itacademy.web_rental_car.model.domain.User;
import com.itacademy.web_rental_car.service.OrderService;
import com.itacademy.web_rental_car.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/updateOrderStatus")
    public String updateOrderStatus(@RequestParam("orderId") Integer orderId,
                                    @RequestParam("status") String status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/adminPage";
    }

    @PostMapping("/create")
    public String createOrder(@RequestParam("carId") Integer carId,
                              @RequestParam("startDate") Date startDate,
                              @RequestParam("endDate") Date endDate,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        return orderService.createOrder(carId, startDate, endDate, principal, redirectAttributes);
    }

    @GetMapping("/placingOrder")
    public String placingOrder(Model model) {
        return orderService.placingOrder(model);
    }

    @PostMapping("/payOrder")
    public String payOrder(@RequestParam("orderId") Integer orderId, Model model, HttpSession session) {
        String redirectPage = orderService.payOrder(orderId, model, session);
        Order order = orderService.getOrderById(orderId);
        User authenticatedUser = userService.getAuthenticatedUser(session);
        model.addAttribute("order", order);
        model.addAttribute("authenticatedUser", authenticatedUser);
        model.addAttribute("message", "Order successfully paid");
        return redirectPage;
    }
}























