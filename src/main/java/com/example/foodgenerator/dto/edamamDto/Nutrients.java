package com.example.foodgenerator.dto.edamamDto;


import com.fasterxml.jackson.annotation.JsonProperty;



public record Nutrients(@JsonProperty("ENERC_KCAL")
                        double calories,
                        @JsonProperty("PROCNT")
                        double protein,
                        @JsonProperty("FAT")
                        double fat,
                        @JsonProperty("CHOCDF")
                        double carbs) {



}
