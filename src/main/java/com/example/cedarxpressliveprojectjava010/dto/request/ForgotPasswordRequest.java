package com.example.cedarxpressliveprojectjava010.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ForgotPasswordRequest {
    @NotBlank(message = "email is required!")
    private String email;
}
