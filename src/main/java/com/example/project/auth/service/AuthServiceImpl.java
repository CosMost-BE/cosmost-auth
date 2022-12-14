package com.example.project.auth.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.project.auth.configuration.util.AmazonS3ResourceStorage;
import com.example.project.auth.configuration.util.JwtTokenProvider;
import com.example.project.auth.exception.*;
import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.entity.AuthSns;
import com.example.project.auth.infrastructure.entity.AuthStatus;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.model.Auth;
import com.example.project.auth.requestbody.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthEntityRepository authEntityRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public AuthServiceImpl(AuthEntityRepository authEntityRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder,
                           AmazonS3ResourceStorage amazonS3ResourceStorage, AmazonS3 amazonS3) {
        this.authEntityRepository = authEntityRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.amazonS3ResourceStorage = amazonS3ResourceStorage;
        this.amazonS3 = amazonS3;
    }

    @Override // ????????????
    public AuthEntity createAuth(CreateAuthRequest createAuthRequest, MultipartFile file) {

        FileInfoRequest fileInfoRequest = FileInfoRequest.multipartOf(file, "profile_img");
        amazonS3ResourceStorage.store(fileInfoRequest, file);

        return authEntityRepository.save(
                AuthEntity.builder()
                        .loginId(createAuthRequest.getLoginId())
                        .loginPwd(passwordEncoder.encode(createAuthRequest.getLoginPwd()))
                        .email(createAuthRequest.getEmail())
                        .role(AuthRole.USER)
                        .status(AuthStatus.ACTIVE)
                        .nickname(createAuthRequest.getNickname())
                        .address(createAuthRequest.getAddress())
                        .sns(createAuthRequest.getSns())
                        .married(createAuthRequest.getMarried())
                        .ageGroup(createAuthRequest.getAgeGroup())
                        .profileImgOriginName(fileInfoRequest.getName())
                        .profileImgSaveName(fileInfoRequest.getRemotePath())
                        .profileImgSaveUrl(fileInfoRequest.getUrl())
                        .build());
    }

    @Override // ?????? ????????? ??????
    public boolean checkId(HttpServletRequest request) throws DuplicatedIdException {
        String header = jwtTokenProvider.getHeader(request);
        log.info(String.valueOf(header));
        Optional<AuthEntity> authEntity = authEntityRepository.findByLoginId(header);

        if (authEntity.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override // ?????? ????????? ??????
    public boolean checkNickname(HttpServletRequest request) throws DuplicatedNickname {
        String header = jwtTokenProvider.getHeader(request);
        log.info(String.valueOf(header));
        Optional<AuthEntity> authEntity = authEntityRepository.findByNickname(header);


        if (authEntity.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override // ?????????
    public String updateLoginAuth(UpdateLoginRequest updateLoginRequest) {
        Optional<AuthEntity> auth = authEntityRepository.findByLoginId(updateLoginRequest.getLoginId());

        // ????????????????????? ??????, ???????????? ??????????????? ???????????? ???????????? ??????, ?????? ???????????? ?????? ??????, ???????????? ??????
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(auth != null && encoder.matches(updateLoginRequest.getLoginPwd(), auth.get().getLoginPwd()) &&
                auth.get().getSns().equals(AuthSns.NO) && auth.get().getStatus().equals(AuthStatus.ACTIVE)) {
            return jwtTokenProvider.createToken((auth.get().getId()), String.valueOf(auth.get().getRole()));
        }
        return null;
    }

    @Override // ?????? ??????
    @Transactional
    public void deleteAuthInfo(HttpServletRequest request, UpdateAuthRequest updateAuthRequest,
                                  MultipartFile file) throws WithdrawalCheckNotFound {
        String authId = jwtTokenProvider.getUserPk(jwtTokenProvider.getToken(request));
        Optional<AuthEntity> auth = authEntityRepository.findById(Long.valueOf(authId));

        String securePwd = passwordEncoder.encode(updateAuthRequest.getLoginPwd());
        System.out.println("@@@@@@@@@@@@@" + securePwd);

        // ??????????????? ????????????
        String oldPwd = auth.get().getLoginPwd();
        // ???????????? ??? ????????????
        String pwd = updateAuthRequest.getLoginPwd();

        Optional<AuthEntity> authInfo = Optional.ofNullable(
                authEntityRepository.findById(Long.valueOf(authId)).orElseThrow(
                        UpdateAuthFail::new));

        if (authInfo.isPresent()) {
            if (auth.isPresent() && passwordEncoder.matches(pwd, oldPwd)) {
                authEntityRepository.save(updateAuthRequest.infoDtoEntity(auth.get().getId(), updateAuthRequest, securePwd));
            } else {
                throw new WithdrawalCheckNotFound();
            }
        }
    }

    @Override // ???????????? ??????
    @Transactional
    public void updateAuthInfo(UpdateAuthRequest updateAuthRequest, HttpServletRequest request,
                               MultipartFile file) throws UpdateAuthFail {
        String token = jwtTokenProvider.getToken(request);
        Long id = Long.valueOf(jwtTokenProvider.getUserPk(token));


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Optional<AuthEntity> authInfo = Optional.ofNullable(
                authEntityRepository.findById(id).orElseThrow(
                        UpdateAuthFail::new));

        // ???????????? ??? ????????????
        String securePwd = authInfo.get().getLoginPwd();

        if (authInfo.isPresent() && encoder.matches(updateAuthRequest.getLoginPwd(), securePwd)) {


            if (file!= null && !file.isEmpty()) {
                FileInfoRequest fileInfoRequest = FileInfoRequest.multipartOf(file, "profile_img"); // ????????????
                amazonS3ResourceStorage.store(fileInfoRequest, file);

                AuthEntity authEntity = updateAuthRequest.infoAllDtoEntity(authInfo.get().getId(),
                        updateAuthRequest, securePwd, fileInfoRequest);
                authEntityRepository.save(authEntity);

            } else {
                AuthEntity authEntity = updateAuthRequest.infoDtoEntity(authInfo.get().getId(),
                        updateAuthRequest, securePwd);
                authEntityRepository.save(authEntity);
            }
        } else {
            throw new UpdateAuthFail();
        }
    }

    // ???????????? ??????
    @Override
    public void updatePassword(UpdateAuthRequest updateAuthRequest, HttpServletRequest request, MultipartFile file) throws UpdatePasswordFail {
        String token = jwtTokenProvider.getToken(request);
        Long id = Long.valueOf(jwtTokenProvider.getUserPk(token));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Optional<AuthEntity> authInfo = Optional.ofNullable(
                authEntityRepository.findById(id).orElseThrow(
                        UpdateAuthFail::new));

        // ???????????? ??? ????????????
        String securePwd = authInfo.get().getLoginPwd();

        // ???????????? ?????? ????????? ?????? ???????????? ??????
        String oldPwd = updateAuthRequest.getOldPwd();

        // ????????? ???????????? ??????
        String newPwd = encoder.encode(updateAuthRequest.getNewPwd());

        if (authInfo.isPresent()) {
            if (encoder.matches(oldPwd, securePwd)) {
                AuthEntity authEntity = updateAuthRequest.infoDtoEntity(authInfo.get().getId(),
                        updateAuthRequest, newPwd);
                authEntityRepository.save(authEntity);
            } else {
                throw new UpdatePasswordFail();
            }
        }
    }

    // ???????????? ??????
    @Override
    public Auth readAuth(HttpServletRequest request) throws ReadAuthFail{
        String token = jwtTokenProvider.getToken(request);
        Long id = Long.valueOf(jwtTokenProvider.getUserPk(token));

        Optional<AuthEntity> authEntityList = authEntityRepository.findById(id);
        authEntityList.get().getLoginId();

        return Auth.builder()
                .id(authEntityList.get().getId())
                .loginId(authEntityList.get().getLoginId())
                .loginPwd(authEntityList.get().getLoginPwd())
                .email(authEntityList.get().getEmail())
                .sns(authEntityList.get().getSns())
                .nickname(authEntityList.get().getNickname())
                .address(authEntityList.get().getAddress())
                .ageGroup(authEntityList.get().getAgeGroup())
                .married(authEntityList.get().getMarried())
                .profileImgOriginName(authEntityList.get().getProfileImgOriginName())
                .profileImgSaveName(authEntityList.get().getProfileImgSaveName())
                .profileImgSaveUrl(authEntityList.get().getProfileImgSaveUrl())
                .role(authEntityList.get().getRole())
                .status(authEntityList.get().getStatus())
                .build();
    }

    // ?????? ????????? ?????? ??????
    public Auth readAuthor(HttpServletRequest request) throws ReadAuthorFail {
        String header = jwtTokenProvider.getToken(request);
        Optional<AuthEntity> authEntityList = authEntityRepository.findById(Long.valueOf(header));

        if(authEntityList.isPresent()) {
            return Auth.builder()
                    .id(authEntityList.get().getId())
                    .nickname(authEntityList.get().getNickname())
                    .profileImgOriginName(authEntityList.get().getProfileImgOriginName())
                    .profileImgSaveName(authEntityList.get().getProfileImgSaveName())
                    .profileImgSaveUrl(authEntityList.get().getProfileImgSaveUrl())
                    .build();
        }
        return null;
    }


    // ?????? ???????????? ????????? - ?????????
    @Override
    public AuthEntity createOAuth(CreateOAuthRequest createOAuthRequest, MultipartFile file) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AuthEntity authEntity = null;
        if (file != null && !file.isEmpty()) {
            try {
                FileInfoRequest fileInfoRequest = FileInfoRequest.multipartOf(file, "profile_img"); // ????????????
                amazonS3ResourceStorage.store(fileInfoRequest, file);
                log.info(fileInfoRequest.getName());
                log.info(fileInfoRequest.getUrl());

                authEntity = AuthEntity.builder()
                        .loginId(createOAuthRequest.getEmail())
                        .nickname(createOAuthRequest.getNickname())
                        .email(createOAuthRequest.getEmail())
                        .ageGroup(createOAuthRequest.getAgeGroup())
                        .address(createOAuthRequest.getAddress())
                        .married(createOAuthRequest.getMarried())
                        .sns(AuthSns.YES)
                        .role(AuthRole.USER)
                        .status(AuthStatus.ACTIVE)
                        .profileImgOriginName(fileInfoRequest.getName())
                        .profileImgSaveName(fileInfoRequest.getRemotePath())
                        .profileImgSaveUrl(fileInfoRequest.getUrl())
                        .build();
                authEntityRepository.save(authEntity);

            } catch (Exception e){
                throw new ProfileImgNotFoundException();
            }

        } else {

            log.info("########");
            authEntity = authEntityRepository.save(AuthEntity.builder()
                    .loginId(createOAuthRequest.getEmail())
                    .nickname(createOAuthRequest.getNickname())
                    .email(createOAuthRequest.getEmail())
                    .ageGroup(createOAuthRequest.getAgeGroup())
                    .address(createOAuthRequest.getAddress())
                    .married(createOAuthRequest.getMarried())
                    .sns(AuthSns.YES)
                    .role(AuthRole.USER)
                    .status(AuthStatus.ACTIVE)
                    .build());
            authEntityRepository.save(authEntity);
        }
        return authEntity;
    }
}