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

    @PostMapping("/email")
    @ResponseBody
    public String mailConfirm(@RequestBody String email) throws Exception {
        String code = emailServiceImpl.sendSimpleMessage(email);
        log.info("인증코드 : " + code);
        return code;
    }
}