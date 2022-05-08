package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.LoginDTO;
import org.springframework.security.core.Authentication;

public interface LoginService {
    Authentication login(LoginDTO loginDTO);
    void setUpJWT(Authentication authentication);
    void logOut();
}
