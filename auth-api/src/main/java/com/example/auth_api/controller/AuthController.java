package com.example.auth_api.controller;


import com.example.auth_api.dto.LoginRequest;
import com.example.auth_api.dto.LoginResponse;
import com.example.auth_api.dto.RegisterRequest;
import com.example.auth_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request)
    {
        userService.register(request.getEmail(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).build();


    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request)
    {
        String token = userService.login(request.getEmail(), request.getPassword());
        LoginResponse response = new LoginResponse(token);
        return ResponseEntity.ok(response);
    }

}
