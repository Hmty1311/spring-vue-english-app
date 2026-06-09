package com.engapp.backend.domain.user.service;

import com.engapp.backend.common.exception.AuthenticationException;
import com.engapp.backend.domain.user.model.User;
import com.engapp.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getLoginUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String loginId = authentication.getName();

        return userRepository.findByLoginId(loginId)
                .orElseThrow(() ->
                        new AuthenticationException("AUTH-001"));
    }
}