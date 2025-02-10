package com.kowshik.auth_app_springboot_backend.controller;

import com.kowshik.auth_app_springboot_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String userName, @RequestParam String password){
        userService.registerUser(userName, password);
        return ResponseEntity.ok("User registered Successfully !!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String userName, @RequestParam String password){
        boolean isAuthenticated = userService.login(userName, password);
        if (isAuthenticated){
            return ResponseEntity.ok("User logged Successfully !!");
        } else{
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
