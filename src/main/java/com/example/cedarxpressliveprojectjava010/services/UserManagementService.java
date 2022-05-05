package com.example.cedarxpressliveprojectjava010.services;

import com.example.cedarxpressliveprojectjava010.dto.request.ForgotPasswordRequest;
import com.example.cedarxpressliveprojectjava010.dto.response.MessageResponse;
import com.example.cedarxpressliveprojectjava010.dto.request.ResetPasswordRequest;
import org.springframework.http.ResponseEntity;

public interface UserManagementService {

    ResponseEntity<MessageResponse> forgotPassword(ForgotPasswordRequest request);

    ResponseEntity<MessageResponse> resetPassword(String jwToken, ResetPasswordRequest request);
}
