package com.example.project.auth.controller;


import com.example.project.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/v1/validation")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CheckIdController {
    private final AuthService authService;

    public CheckIdController(AuthService authService) {
        this.authService = authService;
    }

}
