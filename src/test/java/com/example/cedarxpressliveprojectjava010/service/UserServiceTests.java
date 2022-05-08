package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.RegistrationDto;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private ModelMapper mapper;
    @Mock
    private PasswordEncoder passwordEncoder;


    RegistrationDto registrationDto;
    User user;

    @BeforeEach
    public void setup(){


        registrationDto = RegistrationDto.builder()
                .firstName("first")
                .lastName("last")
                .email("email")
                .password("1234")
                .confirmPassword("1234")
                .build();

       user = new User();
       user.setId(1L);
       user.setEmail("email");
       user.setPassword("1234");
       user.setLastName("last");
       user.setFirstName("first");

    }

    @Test
    public void givenUserObject_whenCreatUser_thenSaveUser(){


        given(userRepository.existsByEmail("email")).willReturn(false);
        given(passwordEncoder.encode(registrationDto.getPassword())).willReturn("1234");
        given(mapper.map(registrationDto,User.class)).willReturn(user);
        given(userRepository.save(any())).willReturn(user);
        given(mapper.map(user,RegistrationDto.class)).willReturn(registrationDto);

        ResponseEntity<RegistrationDto> response = userService.registerUser(registrationDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(registrationDto);

    }

    @Test
    public void givenUserObject_whenCreatUser_thenThrowException(){

        given(userRepository.existsByEmail("email")).willReturn(true);

        Throwable thrown = catchThrowable(()-> userService.registerUser(registrationDto));

        assertThat(thrown).isInstanceOf(RuntimeException.class)
                .hasMessage("User already exist");

    }

    @Test
    public void givenUserObject_whenPasswordsDoNotMatch_thenThrowException(){
        registrationDto.setConfirmPassword("anotherString");
        Throwable thrown = catchThrowable(()-> userService.registerUser(registrationDto));
        assertThat(thrown).isInstanceOf(RuntimeException.class)
                .hasMessage("Passwords do not match");
    }

}
