package com.example.bambi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Pattern.List({
            @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter"),
            @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter"),
            @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one number"),
            @Pattern(regexp = ".*[!@#$%^&*()\\-_+={}\\[\\]|\\\\:;\"'<>,.?/].*", message = "Password must contain at least one special character")
    })
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;


    // Constructor with parameters
    public Admin(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Default constructor
    public Admin() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}