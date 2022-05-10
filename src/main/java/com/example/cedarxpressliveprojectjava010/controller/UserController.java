package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.RegistrationDto;
import com.example.cedarxpressliveprojectjava010.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/cerderXpress/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {


    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationDto> registerUser(@Valid @RequestBody RegistrationDto registrationDto){
        return userService.registerUser(registrationDto);

    }
}
