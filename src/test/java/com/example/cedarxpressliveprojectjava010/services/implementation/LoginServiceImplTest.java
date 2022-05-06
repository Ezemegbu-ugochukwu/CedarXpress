package com.example.cedarxpressliveprojectjava010.services.implementation;

import com.example.cedarxpressliveprojectjava010.configuration.jwt.JwtTokenProvider;
import com.example.cedarxpressliveprojectjava010.dto.LoginDTO;
import com.example.cedarxpressliveprojectjava010.exception.IncorrectPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {LoginServiceImpl.class})
@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {
    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtTokenProvider jwtTokenProvider;
    @Mock private HttpServletResponse httpServletResponse;
    @Mock private SecurityContextHolder securityContextHolder;

    @InjectMocks
    private LoginServiceImpl loginService;

    private LoginDTO loginDTO;

    @Mock
    private Authentication authenticatedToken;

    @BeforeEach
    public void setUp() {
        loginDTO = new LoginDTO("user@gmail.com", "1234");
    }


    @DisplayName("Login method should successfully authenticate and return Authentication")
    @Test
    public void shouldAuthenticationToken() {
        given(authenticationManager.authenticate(any())).willReturn(authenticatedToken);
        given(authenticatedToken.isAuthenticated()).willReturn(true);

        var response = loginService.login(loginDTO);

        assertThat(response).isEqualTo(authenticatedToken);
    }

    @DisplayName("Login method should throw Incorrect password if user is not authenticated")
    @Test
    public void shouldThrowException() {
        given(authenticationManager.authenticate(any())).willReturn(authenticatedToken);

        var thrown = catchThrowable(() -> loginService.login(loginDTO));

        assertThat(thrown).isInstanceOf(IncorrectPasswordException.class)
                .hasMessage("The password you inputted is incorrect!");
    }

    @DisplayName("setUpJWT method should store jwt in header")
    @Test
    public void shouldGenerateJWTAndCallNecessaryMethods() {
        given(jwtTokenProvider.generateToken(authenticatedToken)).willReturn("Bearer 123.ABC.&&&");

        loginService.setUpJWT(authenticatedToken);

        verify(jwtTokenProvider, times(1)).generateToken(authenticatedToken);
        verify(httpServletResponse, times(1)).addHeader("Authorization", "Bearer 123.ABC.&&&");
    }

    @DisplayName("Log-Out method should store jwt in header")
    @Test
    public void shouldCallNecessaryMethods() {
        loginService.logOut();
        verify(httpServletResponse, times(1)).reset();
    }
}

