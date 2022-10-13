package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AuthEntityRepository authEntityRepository;

    @Autowired
    public UserDetailsServiceImpl(AuthEntityRepository authEntityRepository) {
        this.authEntityRepository = authEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        AuthEntity authEntity = authEntityRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new UserDetailsImpl(authEntity);
    }
}
