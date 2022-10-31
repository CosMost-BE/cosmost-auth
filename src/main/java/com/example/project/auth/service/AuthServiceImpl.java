package com.example.project.auth.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.project.auth.configuration.utils.AmazonS3ResourceStorage;
import com.example.project.auth.configuration.utils.JwtTokenProvider;
import com.example.project.auth.exception.*;
import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.entity.AuthSns;
import com.example.project.auth.infrastructure.entity.AuthStatus;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.model.Auth;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.FileInfoRequest;
import com.example.project.auth.requestbody.UpdateAuthRequest;
import com.example.project.auth.requestbody.UpdateLoginRequest;
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

    @Override // 회원가입
    public AuthEntity createAuth(CreateAuthRequest createAuthRequest, MultipartFile file) {

        FileInfoRequest fileInfoRequest = FileInfoRequest.multipartOf(file, "profile_img"); // 폴더이름
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

    @Override // 중복 아이디 확인
    public boolean checkId(HttpServletRequest request) throws DuplicatedIdException {
        String header = jwtTokenProvider.getHeader(request);
        log.info(String.valueOf(header));
//        Optional<AuthEntity> authEntity = authEntityRepository.existsByLoginId(header);
        Optional<AuthEntity> authEntity = authEntityRepository.findByLoginId(header);

        if (authEntity.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override // 중복 닉네임 확인
    public boolean checkNickname(HttpServletRequest request) throws DuplicatedNickname {
        String header = jwtTokenProvider.getHeader(request);
        log.info(String.valueOf(header));
//        Optional<AuthEntity> authEntity = authEntityRepository.existsByNickname(header);
        Optional<AuthEntity> authEntity = authEntityRepository.findByNickname(header);


        if (authEntity.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override // 로그인
    public String updateLoginAuth(UpdateLoginRequest updateLoginRequest) {
        // optional
        Optional<AuthEntity> auth = authEntityRepository.findByLoginId(updateLoginRequest.getLoginId());

        // 회원가입했는지 비교, 넘겨받은 비밀번호와 암호화된 비밀번호 비교, 소셜 로그인 여부 비교, 회원탈퇴 비교
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(auth != null && encoder.matches(updateLoginRequest.getLoginPwd(), auth.get().getLoginPwd()) &&
                auth.get().getSns().equals(AuthSns.NO) && auth.get().getStatus().equals(AuthStatus.ACTIVE)) {
            return jwtTokenProvider.createToken((auth.get().getId()), String.valueOf(auth.get().getRole()));
        }
        return null;
    }

    @Override // 회원 탈퇴
    @Transactional
    public boolean deleteAuthInfo(HttpServletRequest request, UpdateAuthRequest updateAuthRequest,
                                  MultipartFile file) throws WithdrawalCheckNotFound {
            String authId = jwtTokenProvider.getUserPk(jwtTokenProvider.getToken(request));
//            Long id = Long.valueOf(jwtTokenProvider.getUserPk(authId));
            Optional<AuthEntity> auth = authEntityRepository.findById(Long.valueOf(authId));


            String securePwd = passwordEncoder.encode(updateAuthRequest.getLoginPwd());
            System.out.println("@@@@@@@@@@@"+securePwd);

            // 회원가입시 비밀번호
            String oldPwd = auth.get().getLoginPwd();
            // 회원탈퇴 시 비밀번호
            String newPwd = updateAuthRequest.getLoginPwd();

            Optional<AuthEntity> authInfo = Optional.ofNullable(
                    authEntityRepository.findById(Long.valueOf(authId)).orElseThrow(
                            UpdateAuthFail::new));

            if (authInfo.isPresent()) {
                if (auth.isPresent() && passwordEncoder.matches(newPwd, oldPwd)) {
                    authEntityRepository.save(updateAuthRequest.infoDtoEntity(auth.get().getId(), updateAuthRequest, securePwd));
                }
            }
        return false;
    }



    @Override // 회원정보 수정
    @Transactional
    public void updateAuthInfo(UpdateAuthRequest updateAuthRequest, HttpServletRequest request,
                               MultipartFile file) throws UpdateAuthFail {
        String token = jwtTokenProvider.getToken(request);
        Long id = Long.valueOf(jwtTokenProvider.getUserPk(token));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 비밀번호 암호화하여 다시 user 객체에 저장
        String securePwd = encoder.encode(updateAuthRequest.getLoginPwd());

        Optional<AuthEntity> authInfo = Optional.ofNullable(
                authEntityRepository.findById(id).orElseThrow(
                        UpdateAuthFail::new));

        if (authInfo.isPresent()) {

            if (!file.isEmpty()) {

                FileInfoRequest fileInfoRequest = FileInfoRequest.multipartOf(file, "profile_img"); // 폴더이름
                amazonS3ResourceStorage.store(fileInfoRequest, file);

                AuthEntity authEntity = updateAuthRequest.infoAllDtoEntity(authInfo.get().getId(),
                        updateAuthRequest, securePwd, fileInfoRequest);

                authEntityRepository.save(authEntity);

            } else {
                AuthEntity authEntity = updateAuthRequest.infoDtoEntity(authInfo.get().getId(),
                        updateAuthRequest, securePwd);

                authEntityRepository.save(authEntity);
            }

        }
    }

    // 회원정보 조회
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

    // 다른 작성자 정보 조회
    public Auth readAuthor(HttpServletRequest request) throws ReadAuthorFail {
        String header = jwtTokenProvider.getToken(request);
        log.info(header);

        Optional<AuthEntity> authEntityList = authEntityRepository.findById(Long.valueOf(header));

        if(authEntityList.isPresent()) {
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
        return null;
    }
}