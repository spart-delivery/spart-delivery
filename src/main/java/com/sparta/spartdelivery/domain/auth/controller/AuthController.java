package com.sparta.spartdelivery.domain.auth.controller;

import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
import com.sparta.spartdelivery.domain.auth.dto.request.AuthSigninRequestDto;
import com.sparta.spartdelivery.domain.auth.dto.request.AuthSignupRequestDto;
import com.sparta.spartdelivery.domain.auth.dto.response.AuthSigninResponseDto;
import com.sparta.spartdelivery.domain.auth.dto.response.AuthSignupResponseDto;
import com.sparta.spartdelivery.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    // 회원가입
    @PostMapping("/auth/signup")
    public ResponseEntity<CommonResponseDto<AuthSignupResponseDto>> signup(
            @Valid @RequestBody AuthSignupRequestDto signupRequest) {

        AuthSignupResponseDto signupResponse = authService.signup(signupRequest);

        // CommonResponseDto를 사용하여 응답을 생성
        CommonResponseDto<AuthSignupResponseDto> responseDto = new CommonResponseDto<>(
                HttpStatus.CREATED,  // 일반적으로 201 Created를 사용
                "회원가입이 성공적으로 완료되었습니다.",
                signupResponse
        );

        // ResponseEntity를 사용하여 응답 반환 서버가 클라이언트에게 응답을 보내는 부분
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/auth/signin")
    public ResponseEntity<CommonResponseDto<AuthSigninResponseDto>> signin(
            @Valid @RequestBody AuthSigninRequestDto signinRequest) {

        AuthSigninResponseDto signinResponse = authService.signin(signinRequest);

        // CommonResponseDto를 사용하여 응답을 생성
        CommonResponseDto<AuthSigninResponseDto> responseDto = new CommonResponseDto<>(
                HttpStatus.OK,
                "로그인 성공",
                signinResponse
        );

        // ResponseEntity를 사용하여 응답 반환
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}

