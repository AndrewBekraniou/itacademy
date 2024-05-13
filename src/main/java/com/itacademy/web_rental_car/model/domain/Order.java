package com.itacademy.web_rental_car.model.domain;

import com.itacademy.web_rental_car.model.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "order_car")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "confirmationDate")
    private Timestamp confirmationDate;
    @Column(name = "orderStartDate")
    private Date orderStartDate;
    @Column(name = "orderEndDate")
    private Date orderEndDate;
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "total_price")
    private Double totalPrice;

    @PrePersist
    public void prePersist() {
        this.confirmationDate = new Timestamp(System.currentTimeMillis());
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Status> statusList;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Damage damage;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", confirmationDate=" + confirmationDate +
                ", orderStartDate=" + orderStartDate +
                ", orderEndDate=" + orderEndDate +
                ", user=" + (user != null ? user.getId() : null) +
                ", car=" + (car != null ? car.getId() : null) +
                '}';
    }
}
