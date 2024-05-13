package com.itacademy.web_rental_car.model.domain;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "damage")
public class Damage {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "infoDetailsDamage")
    private String infoDetailsDamage;
    @Column(name = "paymentForDamage")
    private double paymentForDamage;

    @OneToOne
    @JoinColumn(name = "order_car_id")
    private Order order;

    @Override
    public String toString() {
        return "Damage{" +
                "id=" + id +
                ", infoDetailsDamage='" + infoDetailsDamage + '\'' +
                ", paymentForDamage=" + paymentForDamage +
                '}';
    }

}
