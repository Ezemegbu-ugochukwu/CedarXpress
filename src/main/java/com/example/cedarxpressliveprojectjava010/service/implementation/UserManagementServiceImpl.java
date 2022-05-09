package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.config.jwt.JwtTokenProvider;
import com.example.cedarxpressliveprojectjava010.dto.request.ForgotPasswordRequest;
import com.example.cedarxpressliveprojectjava010.dto.response.MessageResponse;
import com.example.cedarxpressliveprojectjava010.dto.request.ResetPasswordRequest;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserManagementServiceImpl implements UserManagementService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<MessageResponse> forgotPassword(ForgotPasswordRequest request) {
        String email = request.getEmail();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        String.format("User with email %s doesn't exist!", email)));
        String JWToken = jwtTokenProvider.generateResetPasswordJWT(user);
        String forgotPasswordURL = "http://localhost:8080/reset-password/" + JWToken;

        System.out.println(forgotPasswordURL);
        // send mail method;
         return ResponseEntity.ok(new MessageResponse("Kindly check email for reset Link!"));
    }

    @Override
    public ResponseEntity<MessageResponse> resetPassword(String jwToken, ResetPasswordRequest request) {
        String email = request.getEmail();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        String.format("User with email %s doesn't exist!", email)));
        jwtTokenProvider.validateResetPasswordJWT(jwToken, user.getPassword());

        if (!request.getNewPassword().equals(request.getConfirmPassword()))
            throw new RuntimeException("Password incorrect!");

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Password updated successfully"));

    }
}
