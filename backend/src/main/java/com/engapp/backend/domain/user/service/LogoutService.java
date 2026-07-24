package com.engapp.backend.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogoutService {

    public void logout(Long userId) {
        // JWT認証のためサーバー側で破棄する情報なし
        // 将来 Refresh TokenやBlackListを導入する場合はここに実装する
    }
}
