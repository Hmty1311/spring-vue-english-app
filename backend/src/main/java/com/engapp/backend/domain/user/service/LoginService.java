package com.engapp.backend.domain.user.service;

import com.engapp.backend.common.exception.AuthenticationException;
import com.engapp.backend.domain.user.model.User;
import com.engapp.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User authenticate(String loginId, String password) {

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new AuthenticationException("AUTH-001"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("AUTH-001");
        }

        return user;
    }
}