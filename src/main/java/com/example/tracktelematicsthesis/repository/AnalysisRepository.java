package com.example.tracktelematicsthesis.repository;

import com.example.tracktelematicsthesis.model.AnalysisData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<AnalysisData, String> {
}
