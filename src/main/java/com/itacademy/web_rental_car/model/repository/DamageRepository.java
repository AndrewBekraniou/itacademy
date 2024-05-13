package com.itacademy.web_rental_car.model.repository;

import com.itacademy.web_rental_car.model.domain.Damage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DamageRepository extends JpaRepository<Damage, Integer> {
    Damage findByOrderId(Integer orderId);
}
