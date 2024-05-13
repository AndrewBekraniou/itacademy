package com.itacademy.web_rental_car.service;

import com.itacademy.web_rental_car.dto.DamageDto;
import com.itacademy.web_rental_car.model.domain.Damage;

import java.util.List;

public interface DamageService {

    void saveOrUpdateDamage(DamageDto damageDto);

    Damage findByOrderId(Integer orderId);

    List<Damage> getUserDamages();
}
