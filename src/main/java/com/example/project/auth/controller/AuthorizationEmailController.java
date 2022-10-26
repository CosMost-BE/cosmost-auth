package com.example.project.auth.controller;

import com.example.project.auth.service.AuthService;
import com.example.project.auth.service.email.EmailConfirmService;
import com.example.project.auth.service.email.EmailSenderService;
import com.example.project.auth.service.email.EmailConfirmServiceImpl;
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

    private final EmailConfirmServiceImpl emailServiceImpl;
    private final EmailSenderService emailSenderService;
    private final EmailConfirmService emailConfirmService;

    @Autowired
    public AuthorizationEmailController(EmailConfirmServiceImpl emailServiceImpl, AuthService authService, EmailSenderService emailSenderService, EmailConfirmService emailConfirmService) {
        this.emailServiceImpl = emailServiceImpl;
        this.emailSenderService = emailSenderService;
        this.emailConfirmService = emailConfirmService;
    }

    // 이메일 인증코드 발송
    @GetMapping("/login-id/confirm/{email}")
    public String findId(@PathVariable String email) throws Exception {
        return emailSenderService.sendEmailId(email);

    }

    @GetMapping("/login-pwd/confirm/{email}")
    public String findPw(@PathVariable String email) throws Exception {
        return emailSenderService.sendEmailPwd(email);
    }

    @GetMapping("/email/confirm/{email}")
    public String createConfirmCodeByEmail(@PathVariable String email) throws Exception {
        return emailSenderService.sendConfirmCodeByEmail(email);
    }



//    // 중복이메일 체크
//    @GetMapping("/duplicate/email/{email}")
//    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String code, String email) {
//        log.info("checkEmailDuplicate, {}", email);
//        return ResponseEntity.status(HttpStatus.OK).body(emailServiceImpl.checkEmailDuplicate(code, email));
//    }





    // 이메일인증 코드 확인
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