package com.example.project.auth.controller;

import com.example.project.auth.exception.EmailCodeException;
import com.example.project.auth.requestbody.UpdateEmailRequest;
import com.example.project.auth.service.email.EmailConfirmServiceImpl;
import com.example.project.auth.service.email.EmailSenderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


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

    // 회원가입 시 이메일 인증코드 발송
    @GetMapping("/email/confirm/{email}")
    public String createConfirmCodeByEmail(@PathVariable String email) throws Exception {
        return emailSenderService.sendConfirmCodeByEmail(email);
    }

    // 아이디 찾기 시 이메일 인증코드 발송
    @GetMapping("/id/confirm/{email}")
    public String findId(@PathVariable String email) throws Exception {
        return emailSenderService.sendEmailId(email);
    }

    // 비밀번호 찾기 시 이메일 인증코드 발송
    @GetMapping("/pwd/confirm/{email}")
    public String findPw(@PathVariable String email) throws Exception {
        return emailSenderService.sendEmailPwd(email);
    }

    // 변경된 이메일로 이메일 코드 전송
    @PutMapping("/newemail/confirm/{email}")
    public String updateEmail(@PathVariable String email, @RequestBody UpdateEmailRequest updateEmailRequest, HttpServletRequest request) throws Exception {
        return emailSenderService.sendEmailNewEmail(email, updateEmailRequest, request);
    }





    // 검증 단계 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // 회원가입 시 이메일 인증코드 검증
    @GetMapping("/code/confirm/{code}/{email}")
    public ResponseEntity<Boolean> userEmailConfirm(@PathVariable String code, @PathVariable String email) {
        log.info("userEmailConfirm, {}, {}", code, email);
        return ResponseEntity.status(HttpStatus.OK).body(emailConfirmService.userEmailConfirm(code, email));
    }


    // 아이디 찾기 시 이메일 인증 코드 검증
    @GetMapping("/id/reissue/{code}/{email}")
    public ResponseEntity<String> userIdReissue(@PathVariable String code, @PathVariable String email) throws EmailCodeException {
        log.info("userIdReissue, {}, {}", code, email);
        return ResponseEntity.status(HttpStatus.OK).body(emailConfirmService.userIdReissue(code, email));
    }
    

    // 비밀번호 찾기 시 이메일 인증 코드 검증
    @GetMapping("/pwd/reissue/{code}/{email}")
    public ResponseEntity<Long> userPasswordReissue(@PathVariable String code, @PathVariable String email) throws EmailCodeException {

        log.info("userPasswordReissue, {}, {}", code, email);
        return ResponseEntity.status(HttpStatus.OK).body(emailConfirmService.userPasswordReissue(code, email));
    }



    // 비밀번호 찾기 시 새 비밀번호 입력할 때
    @PutMapping("/pwd/reissue/{id}/{newpwd}")
    public ResponseEntity<Object> userNewpasswordReissue(@PathVariable Long id, @PathVariable String newpwd) {
        log.info("userNewpasswordReissue, {}, {}", id, newpwd);
        emailConfirmService.userNewpasswordReissue(id, newpwd);
        return ResponseEntity.status(HttpStatus.OK).body("새로운 비밀번호가 저장되었습니다.");
    }





    // 검증 단계 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // 이메일 인증 코드 검증 후 변경된 이메일 반영
    @PutMapping("/newemail/reissue/{code}/{email}")
    public ResponseEntity<String> userNewEmailReissue(@PathVariable String code, @PathVariable String email, HttpServletRequest request, @RequestBody @Valid UpdateEmailRequest updateEmailRequest) throws EmailCodeException {
        log.info("userPasswordReissue, {}, {}", code, email);
        emailConfirmService.userNewEmailReissue(code, email, request, updateEmailRequest);
        return ResponseEntity.status(HttpStatus.OK).body("이메일이 변경되었습니다.");
    }
}