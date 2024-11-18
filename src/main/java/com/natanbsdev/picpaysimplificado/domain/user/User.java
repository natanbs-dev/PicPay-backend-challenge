package com.natanbsdev.picpaysimplificado.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private String passowrd;

//    saldo desse usuário
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private userType userType;
}
