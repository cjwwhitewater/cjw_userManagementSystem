package com.example.usermanagement.repository;

import com.example.usermanagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

Page<User> findByNameContaining(String name, Pageable pageable);
}
