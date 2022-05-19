package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.AddressDto;
import com.example.cedarxpressliveprojectjava010.dto.EditUserDetailsDto;
import com.example.cedarxpressliveprojectjava010.dto.RegistrationDto;
import com.example.cedarxpressliveprojectjava010.dto.UpdatePasswordDto;
import com.example.cedarxpressliveprojectjava010.entity.Address;
import com.example.cedarxpressliveprojectjava010.service.AddressService;
import com.example.cedarxpressliveprojectjava010.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cerderXpress/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {


    private final UserService userService;
    private final AddressService addressService;


    @PostMapping("/register")
    public ResponseEntity<RegistrationDto> registerUser(@Valid @RequestBody RegistrationDto registrationDto){
        return userService.registerUser(registrationDto);
    }


    @PutMapping("/update/")
    public ResponseEntity<String> editUserDetails(@RequestBody EditUserDetailsDto editUserDetailsDto){
        userService.editUserDetails(editUserDetailsDto);
        return new ResponseEntity<>(
                "details updated", HttpStatus.OK
        );
    }

    @PostMapping("/address")
    public ResponseEntity<Address> createAddress(@RequestBody AddressDto addressDto){
        return addressService.createAddress(addressDto);
    }

    @PutMapping ("/update-password")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto){
        return userService.updatePassword(updatePasswordDto);
    }
}
