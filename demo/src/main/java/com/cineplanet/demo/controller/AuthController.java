package com.cineplanet.demo.controller;

import com.cineplanet.demo.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody User user) {
//        return ResponseEntity.ok(authService.login(user));
//    }
}
