package com.itacademy.web_rental_car;

import com.itacademy.web_rental_car.model.domain.PassportData;
import com.itacademy.web_rental_car.model.domain.User;
import com.itacademy.web_rental_car.model.repository.PassportDataRepository;
import com.itacademy.web_rental_car.service.UserService;
import com.itacademy.web_rental_car.service.impl.PassportDataServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PassportDataServiceTest {
    @Mock
    private PassportDataRepository passportDataRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private PassportDataServiceImpl passportDataService;

    @Test
    void testSavePassportData() {
        PassportData passportData = new PassportData();
        when(passportDataRepository.save(passportData)).thenReturn(passportData);
        PassportData result = passportDataService.savePassportData(passportData);
        assertEquals(passportData, result);
        verify(passportDataRepository, times(1)).save(passportData);
    }

    @Test
    void testGetPassportDataByUserId() {
        Integer userId = 1;
        PassportData passportData = new PassportData();
        when(passportDataRepository.findByUserId(userId)).thenReturn(passportData);
        PassportData result = passportDataService.getPassportDataByUserId(userId);
        assertEquals(passportData, result);
        verify(passportDataRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testFindByUser() {
        User user = new User();
        PassportData passportData = new PassportData();
        when(passportDataRepository.findByUser(user)).thenReturn(passportData);
        PassportData result = passportDataService.findByUser(user);
        assertEquals(passportData, result);
        verify(passportDataRepository, times(1)).findByUser(user);
    }

    @Test
    void testExistsByPassportNumberForOtherUsers() {
        String passportNumber = "AB123456";
        User user = new User();
        when(passportDataRepository.existsByPassportNumberAndUserNot(passportNumber, user)).thenReturn(true);
        boolean result = passportDataService.existsByPassportNumberForOtherUsers(passportNumber, user);
        assertEquals(true, result);
        verify(passportDataRepository, times(1)).existsByPassportNumberAndUserNot(passportNumber, user);
    }

    @Test
    void testExistsByIdentificationNumberForOtherUsers() {
        String identificationNumber = "ID123456";
        User user = new User();
        when(passportDataRepository.existsByIdentificationNumberAndUserNot(identificationNumber, user)).thenReturn(true);
        boolean result = passportDataService.existsByIdentificationNumberForOtherUsers(identificationNumber, user);
        assertEquals(true, result);
        verify(passportDataRepository, times(1)).existsByIdentificationNumberAndUserNot(identificationNumber, user);
    }

    @Test
    void testSaveOrUpdatePassportData() {
        PassportData passportData = new PassportData();
        passportData.setPassportNumber("AB123456");
        passportData.setIdentificationNumber("ID123456");

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("Ivan");
        User user = new User();
        user.setUsername("Ivan");
        when(userService.getByUsername("Ivan")).thenReturn(user);

        when(passportDataRepository.existsByPassportNumberAndUserNot(eq("AB123456"), any(User.class))).thenReturn(false);
        when(passportDataRepository.existsByIdentificationNumberAndUserNot(eq("ID123456"), any(User.class))).thenReturn(false);
        when(passportDataRepository.findByUser(user)).thenReturn(passportData);
        when(passportDataRepository.save(passportData)).thenReturn(passportData);

        String result = passportDataService.saveOrUpdatePassportData(passportData, principal);

        assertEquals("Data updated successfully", result);

        verify(userService, times(1)).getByUsername("Ivan");
        verify(passportDataRepository, times(1)).existsByPassportNumberAndUserNot(eq("AB123456"), any(User.class));
        verify(passportDataRepository, times(1)).existsByIdentificationNumberAndUserNot(eq("ID123456"), any(User.class));
        verify(passportDataRepository, times(1)).findByUser(user);
        verify(passportDataRepository, times(1)).save(passportData);
    }
}
