package com.itacademy.web_rental_car.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "passport_data")
public class PassportData {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "passportNumber")
    private String passportNumber;
    @Column(name = "identificationNumber")
    private String identificationNumber;
    @Column(name = "birthDate")
    private LocalDate birthDate;
    @Column(name = "gender")
    private String gender;
    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "PassportData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", user=" + user +
                '}';
    }
}
