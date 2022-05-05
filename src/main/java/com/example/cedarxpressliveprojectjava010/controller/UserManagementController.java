package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.request.ForgotPasswordRequest;
import com.example.cedarxpressliveprojectjava010.dto.response.MessageResponse;
import com.example.cedarxpressliveprojectjava010.dto.request.ResetPasswordRequest;
import com.example.cedarxpressliveprojectjava010.services.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserManagementController {
    private final UserManagementService userManagementService;

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request){
        return userManagementService.forgotPassword(request);
    }

    @PostMapping("/reset-password/{jwt-token}")
    public ResponseEntity<MessageResponse> resetPassword( @PathVariable("jwt-token") String jwtToken, @RequestBody ResetPasswordRequest request){
        return userManagementService.resetPassword(jwtToken, request);
    }
}
