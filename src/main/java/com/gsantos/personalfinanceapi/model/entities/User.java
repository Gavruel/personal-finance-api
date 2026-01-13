package com.gsantos.personalfinanceapi.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "",nullable = false)
    private String name;

    @Column(name = "",nullable = false, unique = true)
    private String email;

    @Column(name = "",nullable = false)
    private String password;
}
