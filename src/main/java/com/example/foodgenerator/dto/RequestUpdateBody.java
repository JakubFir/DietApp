package com.example.foodgenerator.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestUpdateBody {
    private String username;
    private String password;
    private String email;
}
