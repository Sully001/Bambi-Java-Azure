package com.example.bambi.repository;

import com.example.bambi.entity.Admin;
import com.example.bambi.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
    Stream<Admin> findAllByRole(Role role);

}
