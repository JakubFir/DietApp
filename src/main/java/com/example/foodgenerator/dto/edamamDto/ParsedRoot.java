package com.example.foodgenerator.dto.edamamDto;

import com.example.foodgenerator.dto.edamamDto.ParsedItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParsedRoot {
    @JsonProperty("parsed")
    private List<ParsedItem> parsed;

}
