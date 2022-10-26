package com.example.project.auth.controller;

import com.example.project.auth.service.AuthService;
import com.example.project.auth.service.EmailService;
import com.example.project.auth.service.EmailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/v1/authorization")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthorizationEmailController {

    private final EmailServiceImpl emailServiceImpl;
    private final EmailService emailService;

    @Autowired
    public AuthorizationEmailController(EmailServiceImpl emailServiceImpl, AuthService authService, EmailService emailService) {
        this.emailServiceImpl = emailServiceImpl;
        this.emailService = emailService;
    }

    @GetMapping("/login-id/confirm/{email}")
    public String findId(@PathVariable String email) throws Exception {
        return emailServiceImpl.sendEmailId(email);

    }

    @GetMapping("/login-pwd/confirm/{email}")
    public String findPw(@PathVariable String email) throws Exception {
        return emailServiceImpl.sendEmailPwd(email);
    }

    @GetMapping("/email/confirm/{email}")
    public String createConfirmCodeByEmail(@PathVariable String email) throws Exception {
        return emailServiceImpl.sendConfirmCodeByEmail(email);
    }

    @GetMapping("/duplicate/email/{email}")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email) {
        log.info("checkEmailDuplicate, {}", email);
        return ResponseEntity.status(HttpStatus.OK).body(emailServiceImpl.checkEmailDuplicate(email));
    }

    @GetMapping("/code/confirm/{code}/{email}")
    public ResponseEntity<Boolean> userEmailConfirm(@PathVariable String code, @PathVariable String email) {
        log.info("userEmailConfirm, {}, {}", code, email);
        return ResponseEntity.status(HttpStatus.OK).body(emailServiceImpl.userEmailConfirm(code, email));
    }

    @GetMapping("/id/reissue/{code}/{email}")
    public ResponseEntity<Boolean> userIdReissue(@PathVariable String code, @PathVariable String email) throws Exception {
        log.info("userPasswordReissue, {}, {}", code, email);
        return ResponseEntity.status(HttpStatus.OK).body(emailServiceImpl.userIdReissue(code, email));
    }

    @GetMapping("/pw/reissue/{code}/{email}")
    public ResponseEntity<Boolean> userPasswordReissue(@PathVariable String code, @PathVariable String email) throws Exception {
        log.info("userPasswordReissue, {}, {}", code, email);
        return ResponseEntity.status(HttpStatus.OK).body(emailServiceImpl.userPasswordReissue(code, email));
    }
}