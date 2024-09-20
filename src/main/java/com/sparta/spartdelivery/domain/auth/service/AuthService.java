package com.sparta.spartdelivery.domain.auth.service;

import com.sparta.spartdelivery.config.JwtUtil;
import com.sparta.spartdelivery.config.PasswordEncoder;
import com.sparta.spartdelivery.domain.auth.dto.request.AuthSigninDtoRequest;
import com.sparta.spartdelivery.domain.auth.dto.request.AuthSignupDtoRequest;
import com.sparta.spartdelivery.domain.auth.dto.response.AuthSigninDtoResponse;
import com.sparta.spartdelivery.domain.auth.dto.response.AuthSignupDtoResponse;
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
    public AuthSignupDtoResponse signUp(AuthSignupDtoRequest signupRequest) {

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

        String bearerToken = jwtUtil.createToken(savedUser.getUserId(), savedUser.getEmail(), userRole);

        return new AuthSignupDtoResponse(bearerToken);
    }

    public AuthSigninDtoResponse signin(AuthSigninDtoRequest signinRequest) {
        User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
                () -> new InvalidRequestException("가입되지 않은 유저입니다."));

        // 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 오류
        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new AuthException("잘못된 비밀번호입니다.");
        }

        String bearerToken = jwtUtil.createToken(user.getUserId(), user.getEmail(), user.getUserRole());

        return new AuthSigninDtoResponse(bearerToken, 200, "success", null);
    }
}
