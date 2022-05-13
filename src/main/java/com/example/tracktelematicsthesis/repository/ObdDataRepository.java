package com.example.tracktelematicsthesis.repository;

import com.example.tracktelematicsthesis.model.ObdData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObdDataRepository extends JpaRepository<ObdData, String> {
}
