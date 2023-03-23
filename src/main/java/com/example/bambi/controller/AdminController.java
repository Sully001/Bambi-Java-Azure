package com.example.bambi.controller;


import com.example.bambi.entity.Admin;
import com.example.bambi.entity.Order;
import com.example.bambi.entity.Users;
import com.example.bambi.repository.AdminRepository;
import com.example.bambi.repository.OrderRepository;
import com.example.bambi.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "register";
    }

    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
        return "redirect:/home";
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/")
    public String getAllUsers(Model model) {
        List<Users> users = usersRepository.findAll();
        System.out.println("Retrieved " + users.size() + " orders from the database");
        model.addAttribute("users", users);
        return "home";
    }
}
