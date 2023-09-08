package com.example.foodgenerator.dto.edamamDto;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;


public record EdamamNutritions( @JsonProperty("nutrients") List<Nutrients> nutrientsList) {

}
