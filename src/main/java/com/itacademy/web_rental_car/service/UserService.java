package com.itacademy.web_rental_car.service;

import com.itacademy.web_rental_car.model.domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserByNameAndPassword(String username, String password);

    void saveUser(User user);

    User getUserByUsername(String username);

    User getAuthenticatedUser();

    User getAuthenticatedUser(HttpSession session);

    User getUserAttribute(String attributeName, HttpSession session);

    User getByUsername(String username);

    void registerUser(String username, String password, String email, String phone);

    User authenticateUser(String inputUsername, String password);



}