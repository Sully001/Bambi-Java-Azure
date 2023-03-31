package com.example.bambi.controller;


import com.example.bambi.entity.*;
import com.example.bambi.repository.AdminRepository;
import com.example.bambi.repository.OrderRepository;
import com.example.bambi.repository.UsersRepository;
import com.example.bambi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProductService productService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Get a list of low stock products
        List<Product> lowStockProducts = productService.getLowStockProducts();
        model.addAttribute("lowStockProducts", lowStockProducts);
        model.addAttribute("admin", new Admin());
        return "register";
    }

    @PostMapping("/register")
    public String registerAdmin(@Valid @ModelAttribute Admin admin, BindingResult bindingResult, Model model) {
        // Get a list of low stock products
        List<Product> lowStockProducts = productService.getLowStockProducts();
        model.addAttribute("lowStockProducts", lowStockProducts);
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            model.addAttribute("errorMessage", errorMessages);
            return "register";
        }
        Optional<Admin> existingAdmin = adminRepository.findByUsername(admin.getUsername());
        if (existingAdmin.isPresent()) {
            model.addAttribute("errorMessage", "Username already exists! Please create another one.");
            return "register";
        } else {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            admin.setRole(Role.EDITOR);
            adminRepository.save(admin);
            model.addAttribute("successMessage", "Editor registered successfully!");
            return "redirect:/manage_editors";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/customer")
    public String getAllUsers(Model model) {
        // Get a list of low stock products
        List<Product> lowStockProducts = productService.getLowStockProducts();
        model.addAttribute("lowStockProducts", lowStockProducts);
        List<Users> users = usersRepository.findAll();
        System.out.println("Retrieved " + users.size() + " orders from the database");
        model.addAttribute("users", users);
        return "customer";
    }

    @GetMapping("/manage_editors")
    public String manageEditors(Model model) {
        List<Admin> editors = ((List<Admin>) adminRepository.findAll()).stream()
                .filter(admin -> admin.getRole() == Role.EDITOR)
                .collect(Collectors.toList());
        // Get a list of low stock products
        List<Product> lowStockProducts = productService.getLowStockProducts();
        model.addAttribute("lowStockProducts", lowStockProducts);
        model.addAttribute("editors", editors);
        return "manage_editors";
    }

    @PostMapping("/delete_editor")
    public String deleteEditor(@RequestParam Long id) {
        adminRepository.deleteById(id);
        return "redirect:/manage_editors";
    }



}
