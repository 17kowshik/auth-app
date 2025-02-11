package com.kowshik.auth_app_springboot_backend.controller;

import com.kowshik.auth_app_springboot_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    public static class UserRequest {
        private String userName;
        private String password;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) {
        String userName = userRequest.getUserName();
        String password = userRequest.getPassword();

        if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username and password cannot be empty.");
        }

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
    public ResponseEntity<String> login(@RequestBody UserRequest userRequest) {
        String userName = userRequest.getUserName();
        String password = userRequest.getPassword();

        if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username and password cannot be empty.");
        }

        String loginResult = userService.login(userName, password);

        return switch (loginResult) {
            case "SUCCESS" -> ResponseEntity.status(HttpStatus.OK)
                    .body("User logged in successfully!");
            case "INVALID_CREDENTIALS" -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect credentials. Please try again.");
            case "USER_NOT_FOUND" -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found. Please register for access.");
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        };
    }
}