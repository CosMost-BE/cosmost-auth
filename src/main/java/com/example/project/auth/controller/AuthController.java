package com.example.project.auth.controller;

import com.example.project.auth.exception.TypeNotFound;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.UpdateAuthRequest;
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
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<String> createAuth(@RequestPart @Valid CreateAuthRequest createAuthRequest,
                                             @RequestPart(value="file", required = false) MultipartFile file) {
        authService.createAuth(createAuthRequest, file);
        return ResponseEntity.ok().body("회원가입이 되었습니다.");
    }

    @PutMapping("")
    public ResponseEntity<?> updateAuthInfo(@RequestPart @Valid UpdateAuthRequest updateAuthRequest, HttpServletRequest request,
                                            @RequestPart(value="file", required = false) MultipartFile file) {
        if (updateAuthRequest.getType().equals("회원정보 수정")) {
            authService.updateAuthInfo(updateAuthRequest, request, file);
            return ResponseEntity.ok("회원정보가 수정되었습니다.");
        }
        else if (updateAuthRequest.getType().equals("회원 탈퇴")) {
            Boolean auth = authService.deleteAuthInfo(request, updateAuthRequest, file);
            if (auth != null) {
                authService.deleteAuthInfo(request, updateAuthRequest, file);
                return ResponseEntity.ok("회원탈퇴가 되었습니다.");
            }
            throw new TypeNotFound();
        }
        else (updateAuthRequest.getType().equals("비밀번호 수정")) {

        }


    }

    @GetMapping("")
    public AuthView readAuth(HttpServletRequest request) {
        return new AuthView(authService.readAuth(request));
    }
}