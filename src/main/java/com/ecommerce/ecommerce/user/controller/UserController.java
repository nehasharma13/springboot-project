package com.ecommerce.ecommerce.user.controller;

import com.ecommerce.ecommerce.security.JwtUtil;
import com.ecommerce.ecommerce.user.entity.User;
import com.ecommerce.ecommerce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Welcome Admin! You have full access.");
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String userAccess() {
        return "This is USER or ADMIN";
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome! You are successfully authenticated.");
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);

    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {

        String token = userService.loginUser(user.getEmail(), user.getPassword());
        return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
    }


}
