package com.itacademy.web_rental_car.model.repository;


import com.itacademy.web_rental_car.model.domain.PassportData;
import com.itacademy.web_rental_car.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassportDataRepository extends JpaRepository<PassportData, Integer> {

    Optional<PassportData> findById(Integer passportId);

    PassportData findByUserId(Integer userId);

    PassportData findByUser(User user);

    boolean existsByPassportNumberAndUserNot(String passportNumber, User user);

    boolean existsByIdentificationNumberAndUserNot(String identificationNumber, User user);
}





