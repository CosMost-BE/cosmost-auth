package com.example.project.auth.controller;

import com.example.project.auth.exception.TypeNotFound;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.UpdateAuthRequest;
import com.example.project.auth.requestbody.UpdateLoginRequest;
import com.example.project.auth.service.AuthService;
import com.example.project.auth.view.AuthView;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequestMapping(value = "/v1/auths")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiResponses({
            @ApiResponse(code=201, message = "회원가입 완료"),
            @ApiResponse(code=401, message = "회원가입이 되지 않았습니다. 다시 확인하세요"),
            @ApiResponse(code=403, message = "권한이 존재하지 않습니다."),
            @ApiResponse(code=404, message = "데이터가 없습니다. 요청한 페이지를 찾을 수 없습니다.")
    })

    @ApiOperation(value = "회원가입을 할 때 쓰는 메소드")
    @ApiImplicitParam(name = "auth", value = "회원가입", dataType = "AuthVoReq")
    @PostMapping("")
    public ResponseEntity<String> createAuth(@RequestBody @Valid CreateAuthRequest createAuthRequest) {
        authService.createAuth(createAuthRequest);
        return ResponseEntity.ok().body("회원가입이 되었습니다.");
    }

    @PutMapping("")
    public ResponseEntity<?> updateAuthInfo(@RequestBody @Valid UpdateAuthRequest updateAuthRequest, HttpServletRequest request) {
        Boolean auth = authService.deleteAuthInfo(request, updateAuthRequest);

        if (updateAuthRequest.getType().equals("회원정보 수정")) {
            authService.updateAuthInfo(updateAuthRequest, request);
            return ResponseEntity.ok("회원정보가 수정되었습니다.");
        }
        if (updateAuthRequest.getType().equals("회원 탈퇴")) {
            if (auth != null) {
                authService.deleteAuthInfo(request, updateAuthRequest);
                return ResponseEntity.ok("회원탈퇴가 되었습니다.");
            }
            throw new TypeNotFound();
        }
        return null;
    }


    @GetMapping("")
    public AuthView readAuth(HttpServletRequest request) {
        return new AuthView(authService.readAuth(request));
    }
}