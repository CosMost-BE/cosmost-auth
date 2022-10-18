package com.example.project.auth.controller;

import com.example.project.auth.service.EmailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/authorization")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthorizationEmailController {
    private final EmailServiceImpl emailServiceImpl;

    @Autowired
    public AuthorizationEmailController(EmailServiceImpl emailServiceImpl) {
        this.emailServiceImpl = emailServiceImpl;
    }

    @GetMapping("/check/{email}")
    public String findPw(@PathVariable String email) throws Exception {
        return emailServiceImpl.sendEmail(email);
    }

    @GetMapping("/email/confirm/{email}")
    public String createConfirmCodeByEmail(@PathVariable String email) throws Exception {
        return emailServiceImpl.sendConfirmCodeByEmail(email);
    }
}