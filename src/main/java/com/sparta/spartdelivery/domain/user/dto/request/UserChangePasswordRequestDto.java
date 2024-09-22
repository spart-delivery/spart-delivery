package com.sparta.spartdelivery.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordRequestDto {

    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
