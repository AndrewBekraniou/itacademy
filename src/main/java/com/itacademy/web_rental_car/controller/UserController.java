package com.itacademy.web_rental_car.controller;

import com.itacademy.web_rental_car.model.domain.Damage;
import com.itacademy.web_rental_car.model.domain.Order;
import com.itacademy.web_rental_car.model.domain.User;
import com.itacademy.web_rental_car.service.*;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Data
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final CarService carService;
    private final CarDataService carDataService;
    private final PassportDataService passportDataService;
    private final DamageService damageService;

    @GetMapping("/userMainPage")
    @ResponseBody
    public Map<String, Object> mainPage(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", authentication != null && authentication.isAuthenticated()
                && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        return response;
    }

    @GetMapping("/adminMainPage")
    @ResponseBody
    public Map<String, Object> adminMainPage(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", authentication != null && authentication.isAuthenticated()
                && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        return response;
    }

    @PostMapping("/registration")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("email") String email,
                               @RequestParam("phone") String phone) {
        userService.registerUser(username, password, email, phone);
        return "redirect:/mainPage";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam("username") String inputUsername,
                               @RequestParam("password") String password,
                               Model model) {
        User user = userService.authenticateUser(inputUsername, password);
        if (user != null) {
            model.addAttribute("user", user);
            return "redirect:/mainPage";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "mainPage";
        }
    }

    @GetMapping("/userPage")
    public String showUserPage(Model model) {
        User authenticatedUser = userService.getAuthenticatedUser();
        if (authenticatedUser != null) {
            model.addAttribute("user", authenticatedUser);
            List<Order> userOrders = orderService.getAllOrdersForUser(authenticatedUser.getId());
            model.addAttribute("orders", userOrders);
            List<Damage> userDamages = damageService.getUserDamages();
            model.addAttribute("damages", userDamages);
            return "userPage";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/adminPage")
    public String showAdminPage(Model model) {
        List<Order> orders = orderService.getAllOrdersWithDetails();
        model.addAttribute("orders", orders);
        return "adminPage";
    }
}







