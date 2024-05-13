package com.itacademy.web_rental_car.model.domain;

import com.itacademy.web_rental_car.model.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "status")
public class Status {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_name")
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", status=" + status +
                ", order=" + order +
                '}';
    }
}

