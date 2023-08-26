package com.example.foodgenerator.dto.edamamDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Nutrients {
    @JsonProperty("ENERC_KCAL")
    private double calories;
    @JsonProperty("PROCNT")
    private double procnmt;
    @JsonProperty("FAT")
    private double fat;
    @JsonProperty("CHOCDF")
    private double chd;
    @JsonProperty("FIBTG")
    private double fib;


}
