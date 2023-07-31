package com.example.steamportfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private int code;
    private String name;
    private String sign;
    private Position position;
}
