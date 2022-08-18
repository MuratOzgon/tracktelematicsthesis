package com.example.tracktelematicsthesis.repository;

import com.example.tracktelematicsthesis.model.CarData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarDataRepository extends JpaRepository<CarData, String> {
}
