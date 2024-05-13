package com.itacademy.web_rental_car;

import com.itacademy.web_rental_car.model.domain.Damage;
import com.itacademy.web_rental_car.model.domain.Order;
import com.itacademy.web_rental_car.model.domain.User;
import com.itacademy.web_rental_car.model.repository.DamageRepository;
import com.itacademy.web_rental_car.service.OrderService;
import com.itacademy.web_rental_car.service.UserService;
import com.itacademy.web_rental_car.service.impl.DamageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DamageServiceTest {

    @InjectMocks
    private DamageServiceImpl damageService;
    @Mock
    private DamageRepository damageRepository;
    @Mock
    private UserService userService;
    @Mock
    private OrderService orderService;


    @Test
    public void testFindByOrderId() {
        Integer orderId = 1;
        Damage expectedDamage = new Damage();
        when(damageRepository.findByOrderId(orderId)).thenReturn(expectedDamage);
        Damage actualDamage = damageService.findByOrderId(orderId);
        assertEquals(expectedDamage, actualDamage);
    }

    @Test
    public void testGetUserDamages_WhenAuthenticatedUserExists() {
        User authenticatedUser = new User();
        authenticatedUser.setId(1);
        when(userService.getAuthenticatedUser()).thenReturn(authenticatedUser);
        Order order1 = new Order();
        order1.setId(1);
        Order order2 = new Order();
        order2.setId(2);
        List<Order> userOrders = new ArrayList<>();
        userOrders.add(order1);
        userOrders.add(order2);
        when(orderService.getAllOrdersForUser(authenticatedUser.getId())).thenReturn(userOrders);
        Damage damage1 = new Damage();
        Damage damage2 = new Damage();
        when(damageRepository.findByOrderId(1)).thenReturn(damage1);
        when(damageRepository.findByOrderId(2)).thenReturn(damage2);
        List<Damage> userDamages = damageService.getUserDamages();
        assertEquals(2, userDamages.size());
    }

    @Test
    public void testGetUserDamages_WhenAuthenticatedUserDoesNotExist() {
        when(userService.getAuthenticatedUser()).thenReturn(null);
        List<Damage> userDamages = damageService.getUserDamages();
        assertEquals(Collections.emptyList(), userDamages);
    }
}
