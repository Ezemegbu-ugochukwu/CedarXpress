package com.example.cedarxpressliveprojectjava010.services.implementation;

import com.example.cedarxpressliveprojectjava010.configuration.jwt.JwtTokenProvider;
import com.example.cedarxpressliveprojectjava010.dto.LoginDTO;
import com.example.cedarxpressliveprojectjava010.exception.IncorrectPasswordException;
import com.example.cedarxpressliveprojectjava010.services.BlacklistService;
import com.example.cedarxpressliveprojectjava010.services.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletResponse httpServletResponse;
    private final HttpServletRequest httpServletRequest;
    private final BlacklistService blacklistService;

    private String incorrectPasswordMessage = "The password you inputted is incorrect!";

    @Override
    public Authentication login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        log.info("Initiating authentication for " + email);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authenticatedToken = authenticationManager.authenticate(authToken);

        if (!authenticatedToken.isAuthenticated()) {
            log.error(email + " Inputted an incorrect password!");
            throw new IncorrectPasswordException(incorrectPasswordMessage);
        }

        SecurityContextHolder.getContext().setAuthentication(authenticatedToken);

        log.info("Successfully logged in {}!", email );
        return authenticatedToken;
    }

    public void setUpJWT(Authentication authentication){
        log.info("Setting up JWT");
        String jwToken = jwtTokenProvider.generateToken(authentication);
        httpServletResponse.addHeader("Authorization", jwToken);
        log.info("JWT Created and stored in header");
    }

    @Override
    public void logOut(){
        log.info("Logging out!");
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        Date expiryDate = jwtTokenProvider.getExpiryDate(token);
        blacklistService.blackListToken(token, expiryDate);
        SecurityContextHolder.clearContext();
        httpServletResponse.reset();
    }
}

