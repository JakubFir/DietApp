package com.example.foodgenerator.edamam.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class EdamamConfig {

    @Value("${edamamUrl}")
    private String edamamUrl;
    @Value("${edamamAppId}")
    private String edamamAppId;
    @Value("${edamamAppKey}")
    private String edamamAppKey;

}
