package com.sparta.spartdelivery.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthSignupRequestDto {

    // 이메일은 빈칸이 불가능 올바른 이메일 형식이어야 함
    @NotBlank(message = "빈칸은 입력이 불가능합니다.")
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

    // 사용자 이름은 빈칸이 불가능
    @NotBlank(message = "빈칸은 입력이 불가능합니다.")
    private String username;

    // 비밀번호는 빈칸이 불가능
    // 영문 대소문자 + 숫자 + 특수문자를 포함한 8글자 이상의 형식이어야 함
    @NotBlank(message = "빈칸은 입력이 불가능합니다.")
    @Pattern(regexp = "^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$",
            message = "대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함하여 최소 8글자 이상이어야 합니다.")
    private String password;

    // 사용자 역할은 빈칸이 불가능
    @NotBlank(message = "빈칸은 입력이 불가능합니다.")
    private String userRole;

}