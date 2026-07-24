package com.engapp.backend.web.logout;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.engapp.backend.domain.user.facade.LogoutFacade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.engapp.backend.common.util.SecurityUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LogoutController {

    private final LogoutFacade logoutFacade;

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {

        Long userId = SecurityUtil.getLoginUserId();

        logoutFacade.logout(userId);
    }

}
