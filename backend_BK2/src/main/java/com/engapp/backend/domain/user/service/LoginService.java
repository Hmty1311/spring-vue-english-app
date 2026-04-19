package com.engapp.backend.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.engapp.backend.domain.common.exception.AuthenticationException;
import com.engapp.backend.domain.user.model.User;
import com.engapp.backend.domain.user.repository.UserRepository;
import com.engapp.backend.web.dto.LoginResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public LoginResponse login(String userName, String password) {

        try {
            Optional<User> user = userRepository.findByUserNameAndPassword(userName, password);

            if (user.isEmpty()) {
                throw new AuthenticationException("AUTH-001", "Invalid username or password");
            }

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserId(user.get().getId());
            loginResponse.setUserName(user.get().getName());
            // TODO: Generate JWT token
            // loginResponse.setToken(generateToken(user.get()));
            
            return loginResponse;

        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthenticationException("AUTH-001", "Login failed");
        }
    }

}
