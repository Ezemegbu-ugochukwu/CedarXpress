package com.example.cedarxpressliveprojectjava010.services;

import com.example.cedarxpressliveprojectjava010.dto.LoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface LoginService {
    Authentication login(LoginDTO loginDTO);
    void setUpJWT(Authentication authentication);
    void logOut();
}
