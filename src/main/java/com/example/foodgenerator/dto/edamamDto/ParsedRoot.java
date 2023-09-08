package com.example.foodgenerator.dto.edamamDto;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

public record ParsedRoot(@JsonProperty("parsed")
                         List<ParsedItem> parsed) {


}
