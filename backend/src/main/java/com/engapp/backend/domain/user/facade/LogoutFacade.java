package com.engapp.backend.domain.user.facade;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.engapp.backend.domain.user.service.LogoutService;

@Component
@RequiredArgsConstructor
public class LogoutFacade {
    
    private final LogoutService logoutService;

    public void logout(Long userId) {
        logoutService.logout(userId);
    }

}
