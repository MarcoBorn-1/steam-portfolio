package com.example.steamportfolio.entity;

import com.example.steamportfolio.entity.enums.Category;
import com.example.steamportfolio.entity.enums.Quality;
import com.example.steamportfolio.entity.enums.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String pictureURL;
    private boolean commodity;
    private Category category;
    private Type type;
    private Quality quality;
}
