package com.example.foodgenerator.repository;

import com.example.foodgenerator.domain.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietRepository extends JpaRepository<Diet, Long> {
}
