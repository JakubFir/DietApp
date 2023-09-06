package com.example.foodgenerator.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double protein;
    private double fat;
    private double carbs;
    @OneToMany(mappedBy = "diet")
    private List<User> users;

    public Diet(String name, double protein, double fat, double carbs, List<User> users) {
        this.name = name;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.users = users;
    }
}
