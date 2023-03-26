package com.example.bambi.repository;

import com.example.bambi.entity.Order;
import com.example.bambi.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {

    List<Users> findAll();
}
