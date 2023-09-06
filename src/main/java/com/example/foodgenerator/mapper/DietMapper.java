package com.example.foodgenerator.mapper;

import com.example.foodgenerator.domain.Diet;
import com.example.foodgenerator.dto.DietDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DietMapper {

    public DietDto mapToDietDto(Diet dietDto) {
        return new DietDto(
                dietDto.getName(),
                dietDto.getProtein(),
                dietDto.getFat(),
                dietDto.getCarbs()
        );
    }
}
