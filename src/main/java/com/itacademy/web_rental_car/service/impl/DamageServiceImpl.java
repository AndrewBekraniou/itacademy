package com.itacademy.web_rental_car.service.impl;

import com.itacademy.web_rental_car.dto.DamageDto;
import com.itacademy.web_rental_car.model.domain.Damage;
import com.itacademy.web_rental_car.model.domain.Order;
import com.itacademy.web_rental_car.model.domain.User;
import com.itacademy.web_rental_car.model.repository.DamageRepository;
import com.itacademy.web_rental_car.model.repository.OrderRepository;
import com.itacademy.web_rental_car.service.DamageService;
import com.itacademy.web_rental_car.service.OrderService;
import com.itacademy.web_rental_car.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DamageServiceImpl implements DamageService {

    private final DamageRepository damageRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderService orderService;

    public void saveOrUpdateDamage(DamageDto damageDto) {
        Damage existingDamage = damageRepository.findByOrderId(damageDto.getOrderId());
        if (existingDamage != null) {
            existingDamage.setInfoDetailsDamage(damageDto.getInfoDetailsDamage());
            existingDamage.setPaymentForDamage(damageDto.getPaymentForDamage());
            damageRepository.save(existingDamage);
        } else {
            Order order = orderRepository.findById(damageDto.getOrderId()).orElseThrow(() -> new IllegalArgumentException("Order not found"));
            Damage damage = new Damage();
            damage.setInfoDetailsDamage(damageDto.getInfoDetailsDamage());
            damage.setPaymentForDamage(damageDto.getPaymentForDamage());
            damage.setOrder(order);
            damageRepository.save(damage);
        }
    }

    @Override
    public Damage findByOrderId(Integer orderId) {
        return damageRepository.findByOrderId(orderId);
    }


    @Override
    public List<Damage> getUserDamages() {
        User authenticatedUser = userService.getAuthenticatedUser();
        if (authenticatedUser != null) {
            List<Order> userOrders = orderService.getAllOrdersForUser(authenticatedUser.getId());
            List<Damage> userDamages = new ArrayList<>();
            for (Order order : userOrders) {
                Damage damage = findByOrderId(order.getId());
                if (damage != null) {
                    userDamages.add(damage);
                }
            }
            return userDamages;
        } else {
            return Collections.emptyList();
        }

    }

}