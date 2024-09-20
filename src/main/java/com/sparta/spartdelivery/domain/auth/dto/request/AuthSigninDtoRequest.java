package com.sparta.spartdelivery.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthSigninDtoRequest {

    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;
}
