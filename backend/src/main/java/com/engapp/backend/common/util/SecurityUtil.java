package com.engapp.backend.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

   
public class SecurityUtil {

    public static Long getLoginUserId() {

        Authentication authentication = 
            SecurityContextHolder.getContext()
                .getAuthentication();
        
                if(authentication == null ||
                    authentication.getPrincipal() == null){

                    return null;
                }

                return (Long) authentication.getPrincipal();
            }
}
