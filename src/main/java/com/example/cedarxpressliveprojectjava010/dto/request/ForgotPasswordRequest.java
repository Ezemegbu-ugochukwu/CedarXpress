package com.example.cedarxpressliveprojectjava010.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class ForgotPasswordRequest {
    @NotBlank(message = "email is required!")
    private String email;
}
