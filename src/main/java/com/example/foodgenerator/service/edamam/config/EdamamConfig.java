package com.example.foodgenerator.service.edamam.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Getter
public class EdamamConfig {

    @Value("${edamam.url}")
    private String edamamUrl;
    @Value("${edamam.app.id}")
    private String edamamAppId;
    @Value("${edamam.app.key}")
    private String edamamAppKey;

}
