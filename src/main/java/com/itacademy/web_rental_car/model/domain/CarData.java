package com.itacademy.web_rental_car.model.domain;

import com.itacademy.web_rental_car.model.domain.enums.CarBodyType;
import com.itacademy.web_rental_car.model.domain.enums.CarFuelType;
import com.itacademy.web_rental_car.model.domain.enums.CarTransmission;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "car_data")
public class CarData {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "color")
    private String color;
    @Column(name = "model")
    private String model;
    @Column(name = "yearOfIssue")
    private Integer yearOfIssue;
    @Column(name = "rentPricePerDay")
    private double rentPricePerDay;

    @Column(name = "bodyType")
    @Enumerated(EnumType.STRING)
    private CarBodyType bodyType;

    @Column(name = "fuelType")
    @Enumerated(EnumType.STRING)
    private CarFuelType fuelType;

    @Column(name = "transmission")
    @Enumerated(EnumType.STRING)
    private CarTransmission transmission;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    @Override
    public String toString() {
        return "CarData{" +
                "id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", yearOfIssue=" + yearOfIssue +
                ", rentPricePerDay=" + rentPricePerDay +
                ", bodyType=" + bodyType +
                ", fuelType=" + fuelType +
                ", transmission=" + transmission +
                ", car=" + (car != null ? car.getId() : null) +
                '}';
    }
}
