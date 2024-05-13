package com.itacademy.web_rental_car.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "car")
public class Car {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "reg_number")
    private String regNumber;
    @Column(name = "available")
    private String available;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    private CarData carData;

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", regNumber='" + regNumber + '\'' +
                ", available='" + available + '\'' +
                ", orders=" + orders +
                ", carData=" + carData +
                '}';
    }
}
