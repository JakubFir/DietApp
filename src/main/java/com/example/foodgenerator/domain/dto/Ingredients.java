package com.example.foodgenerator.domain.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Ingredients {
    @Id
    private Long id;
    private String name;
}
