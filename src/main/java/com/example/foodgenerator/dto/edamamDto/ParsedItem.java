package com.example.foodgenerator.dto.edamamDto;

import com.example.foodgenerator.dto.edamamDto.Food;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParsedItem {
    @JsonProperty("food")
    private Food food;
}
