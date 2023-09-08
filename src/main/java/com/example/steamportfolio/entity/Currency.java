package com.example.steamportfolio.entity;

import com.example.steamportfolio.entity.enums.Position;
import jakarta.persistence.*;
import lombok.*;

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
