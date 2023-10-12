package com.example.foodgenerator.dto;

import java.io.Serializable;

public record DietDto(String name, double protein, double fat, double carbs) implements Serializable {

}
