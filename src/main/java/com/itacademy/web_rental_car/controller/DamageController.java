package com.itacademy.web_rental_car.controller;

import com.itacademy.web_rental_car.dto.DamageDto;
import com.itacademy.web_rental_car.model.domain.Order;
import com.itacademy.web_rental_car.service.DamageService;
import com.itacademy.web_rental_car.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class DamageController {

    private final DamageService damageService;
    private final OrderService orderService;

    @GetMapping("/admin/order/{orderId}/damage")
    public String showDamageForm(@PathVariable("orderId") Integer orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        return "damageForm";
    }

    @PostMapping("/admin/order/saveDamage")
    public String saveDamage(@ModelAttribute("damageDto") DamageDto damageDto) {
        damageService.saveOrUpdateDamage(damageDto);
        return "redirect:/adminPage";
    }

    @PostMapping("/admin/order/sendToUserPage")
    public String sendToUserPage(@RequestParam("orderId") Integer orderId) {
        return "redirect:/adminPage";
    }

    @PostMapping("/user/order/pay")
    public String payOrder(@RequestParam("orderId") Integer orderId) {
        return "redirect:/userPage";
    }
}