package com.sparta.spartdelivery.domain.kakaoUser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
import com.sparta.spartdelivery.config.JwtUtil;
import com.sparta.spartdelivery.domain.kakaoUser.dto.KakaoLoginRequestDto;
import com.sparta.spartdelivery.domain.kakaoUser.service.KakaoUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class kakaoUserController {

    private final KakaoUserService kakaoUserService;

    @PostMapping("/auth/loginKakao")
    public ResponseEntity<CommonResponseDto<?>> kakaoLogin (@RequestParam String code,
                                                            @RequestBody KakaoLoginRequestDto kakaoLoginRequestDto,
                                                            HttpServletResponse response) throws JsonProcessingException {

        String token = kakaoUserService.kakaoLogin(code, kakaoLoginRequestDto, response);

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7));
        cookie.setPath("https://kapi.kakao.com/v2/user/me");
        response.addCookie(cookie);

        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", token), HttpStatus.OK);
    }
}
