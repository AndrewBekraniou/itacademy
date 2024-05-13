package com.itacademy.web_rental_car.service.impl;

import com.itacademy.web_rental_car.model.domain.User;
import com.itacademy.web_rental_car.model.domain.enums.UserRole;
import com.itacademy.web_rental_car.model.repository.UserRepository;
import com.itacademy.web_rental_car.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void registerUser(String username, String password, String email, String phone) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!email.matches("^[A-Za-z0-9]*@[A-Za-z0-9]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        if (!phone.matches("^\\+[0-9]{1,12}$")) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setPhone(phone);
        user.setRoles(Collections.singletonList(UserRole.ROLE_USER));
        saveUser(user);
    }

    @Override
    public User getUserByNameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public User authenticateUser(String inputUsername, String password) {
        User user = getUserByNameAndPassword(inputUsername, password);
        return user;
    }
    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return (User) principal;
            }
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getAuthenticatedUser(HttpSession session) {
        return getUserAttribute("authenticatedUser", session);
    }

    @Override
    public User getUserAttribute(String attributeName, HttpSession session) {
        return (User) session.getAttribute(attributeName);
    }
}






