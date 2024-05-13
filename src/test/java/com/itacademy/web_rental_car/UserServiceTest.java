package com.itacademy.web_rental_car;

import com.itacademy.web_rental_car.model.domain.User;
import com.itacademy.web_rental_car.model.repository.UserRepository;
import com.itacademy.web_rental_car.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    User expectedUser = new User();

    @BeforeEach
    public void setUp() {
        expectedUser.setUsername("Andrew");
        when(userRepository.findByUsername("Andrew")).thenReturn(expectedUser);
    }

    @Test
    public void testGetByUsername() {
        String username = "Andrew";
        User actualUser = userService.getUserByUsername(username);
        assertEquals(expectedUser, actualUser);
        verify(userRepository).findByUsername(username);
    }
}