package com.engapp.backend.domain.user.service;

import com.engapp.backend.domain.user.model.User;
import com.engapp.backend.domain.user.repository.UserRepository;
import com.engapp.backend.common.exception.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User authenticate(String loginId, String password) {

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new AuthenticationException());

        // 仮：平文比較（あとで必ずハッシュ化に変更）
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException();
        }

        return user;
    }
}