package com.sparta.spartdelivery.domain.auth.service;

import com.sparta.spartdelivery.config.JwtUtil;
import com.sparta.spartdelivery.config.PasswordEncoder;
import com.sparta.spartdelivery.domain.auth.dto.request.AuthSigninDtoRequest;
import com.sparta.spartdelivery.domain.auth.dto.request.AuthSignupDtoRequest;
import com.sparta.spartdelivery.domain.auth.dto.response.AuthSigninResponseDto;
import com.sparta.spartdelivery.domain.auth.dto.response.AuthSignupResponseDto;
import com.sparta.spartdelivery.domain.auth.exception.AuthException;
import com.sparta.spartdelivery.domain.user.entity.User;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import com.sparta.spartdelivery.domain.user.exception.InvalidRequestException;
import com.sparta.spartdelivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthSignupResponseDto signup(AuthSignupDtoRequest signupRequest) {
        // 비밀번호 조건
        if (signupRequest.getPassword().length() < 8 ||
                !signupRequest.getPassword().matches(".*[a-z].*") || // 소문자 포함
                !signupRequest.getPassword().matches(".*[A-Z].*") || // 대문자 포함
                !signupRequest.getPassword().matches(".*\\d.*") ||   // 숫자 포함
                !signupRequest.getPassword().matches(".*[!@#$%^&*(),.?\":{}|<>].*")) { // 특수문자 포함
            throw new InvalidRequestException("비밀번호는 8자 이상이어야 하고, 영문 대소문자, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        UserRole userRole = UserRole.of(signupRequest.getUserRole());

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new InvalidRequestException("이미 존재하는 이메일입니다.");
        }

        User newUser = new User(
                signupRequest.getEmail(),
                encodedPassword,
                userRole
        );
        User savedUser = userRepository.save(newUser);

        String bearerToken = jwtUtil.createToken(savedUser.getId(), savedUser.getEmail(), userRole);

        return new AuthSignupResponseDto(bearerToken);
    }

    public AuthSigninResponseDto signin(AuthSigninDtoRequest signinRequest) {
        User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
                () -> new InvalidRequestException("가입되지 않은 유저입니다."));

        // 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 오류
        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new AuthException("잘못된 비밀번호입니다.");
        }

        String bearerToken = jwtUtil.createToken(user.getId(), user.getEmail(), user.getUserRole());

        return new AuthSigninResponseDto(bearerToken);
    }


}
