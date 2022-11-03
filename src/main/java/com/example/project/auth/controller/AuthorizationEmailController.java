package com.example.project.auth.controller;

import com.example.project.auth.service.email.EmailConfirmServiceImpl;
import com.example.project.auth.service.email.EmailSenderServiceImpl;
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

    private final EmailConfirmServiceImpl emailConfirmService;
    private final EmailSenderServiceImpl emailSenderService;

    @Autowired
    public AuthorizationEmailController(EmailConfirmServiceImpl emailConfirmService, EmailSenderServiceImpl emailSenderService) {
        this.emailConfirmService = emailConfirmService;
        this.emailSenderService = emailSenderService;
    }

    // 이메일 인증코드 발송
    @GetMapping("/id/confirm/{email}")
    public String findId(@PathVariable String email) throws Exception {
        return emailSenderService.sendEmailId(email);

    }

    @GetMapping("/pwd/confirm/{email}")
    public String findPw(@PathVariable String email) throws Exception {
        return emailSenderService.sendEmailPwd(email);
    }

    @GetMapping("/email/confirm/{email}")
    public String createConfirmCodeByEmail(@PathVariable String email) throws Exception {
        return emailSenderService.sendConfirmCodeByEmail(email);
    }

    // 중복이메일 체크Z
    @GetMapping("/duplicate/email/{email}")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String code, String email) throws Exception {
        log.info("checkEmailDuplicate, {}", email);
        return ResponseEntity.status(HttpStatus.OK).body(emailSenderService.checkEmailDuplicate(code, email));
    }

    // 이메일 인증코드 확인
    @GetMapping("/code/confirm/{code}/{email}")
    public ResponseEntity<Boolean> userEmailConfirm(@PathVariable String code, @PathVariable String email) {
        log.info("userEmailConfirm, {}, {}", code, email);
        return ResponseEntity.status(HttpStatus.OK).body(emailConfirmService.userEmailConfirm(code, email));
    }

    @GetMapping("/id/reissue/{code}/{email}")
    public ResponseEntity<Boolean> userIdReissue(@PathVariable String code, @PathVariable String email) throws Exception {
        log.info("userIdReissue, {}, {}", code, email);
        return ResponseEntity.status(HttpStatus.OK).body(emailConfirmService.userIdReissue(code, email));
    }

    @GetMapping("/pwd/reissue/{code}/{email}")
    public ResponseEntity<Boolean> userPasswordReissue(@PathVariable String code, @PathVariable String email) throws Exception {
        log.info("userPasswordReissue, {}, {}", code, email);
        return ResponseEntity.status(HttpStatus.OK).body(emailConfirmService.userPasswordReissue(code, email));
    }
}