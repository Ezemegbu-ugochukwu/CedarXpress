package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.EditUserDetailsDto;
import com.example.cedarxpressliveprojectjava010.dto.RegistrationDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<RegistrationDto> registerUser(RegistrationDto registrationDto);
    void editUserDetails(EditUserDetailsDto editUserDetailsDto);
}
