package com.example.tracktelematicsthesis.repository;

import com.example.tracktelematicsthesis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
