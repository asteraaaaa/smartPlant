package com.csc3700.smartplant.repository;

import com.csc3700.smartplant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}