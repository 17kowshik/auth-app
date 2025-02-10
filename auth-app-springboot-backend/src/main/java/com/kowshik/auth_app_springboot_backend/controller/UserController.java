package com.kowshik.auth_app_springboot_backend.controller;

import com.kowshik.auth_app_springboot_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> registerUser(@RequestParam String userName, @RequestParam String password) {
        String response = userService.registerUser(userName, password);

        return switch (response) {
            case "USER_ALREADY_EXISTS" -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User already exists");
            case "PASSWORD_NOT_STRONG_ENOUGH" -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password must contain at least one uppercase letter, one lowercase letter, one special character, and be at least 8 characters long.");
            case "USER_REGISTERED_SUCCESSFULLY" -> ResponseEntity.status(HttpStatus.OK)
                    .body("User registered successfully");
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unknown error occurred");
        };
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String userName, @RequestParam String password){
        String loginResult = userService.login(userName, password);

        return switch (loginResult) {
            case "SUCCESS" -> ResponseEntity.ok("User logged in successfully!");
            case "INVALID_CREDENTIALS" -> ResponseEntity.status(401).body("Incorrect credentials. Please try again.");
            case "USER_NOT_FOUND" -> ResponseEntity.status(404).body("User not found. Please register for access.");
            default -> ResponseEntity.status(500).body("An unexpected error occurred.");
        };
    }
}
