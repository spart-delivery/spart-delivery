package com.sparta.spartdelivery.domain.auth.service;

import com.sparta.spartdelivery.config.JwtUtil;
import com.sparta.spartdelivery.config.PasswordEncoder;
import com.sparta.spartdelivery.domain.auth.dto.request.AuthSigninRequestDto;
import com.sparta.spartdelivery.domain.auth.dto.request.AuthSignupRequestDto;
import com.sparta.spartdelivery.domain.auth.dto.response.AuthSigninResponseDto;
import com.sparta.spartdelivery.domain.auth.dto.response.AuthSignupResponseDto;
import com.sparta.spartdelivery.domain.auth.exception.AuthException;
import com.sparta.spartdelivery.domain.user.entity.User;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import com.sparta.spartdelivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    // 필요한 종속성 주입 (UserRepository, PasswordEncoder, JwtUtil)
    private final UserRepository userRepository;  // 유저 데이터를 관리하는 레포지토리
    private final PasswordEncoder passwordEncoder;  // 비밀번호 암호화를 위한 인코더
    private final JwtUtil jwtUtil;  // JWT 토큰을 생성하고 검증하는 유틸리티 클래스

    // 회원가입 기능
    @Transactional
    public AuthSignupResponseDto signup(AuthSignupRequestDto signupRequest) {
        // 이메일 중복 체크 //검증 로직 먼저 올리기
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new AuthException("이미 존재하는 이메일입니다.");
        }

        // 사용자 이름 중복 체크
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new AuthException("이미 존재하는 사용자 이름입니다.");
        }
        // 사용자가 입력한 역할(Role)을 UserRole enum으로 변환
        UserRole userRole = UserRole.of(signupRequest.getUserRole());

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());



        // 새로운 User 객체 생성
        User newUser = new User(
                signupRequest.getEmail(),
                signupRequest.getUsername(),
                encodedPassword,  // 암호화된 비밀번호 저장
                userRole  // 유저 역할 설정
        );

        // 유저를 DB에 저장
        User savedUser = userRepository.save(newUser);

        // 저장된 유저 정보를 바탕으로 JWT 토큰 생성
        String bearerToken = jwtUtil.createToken(savedUser.getUserId(), savedUser.getEmail(), userRole);

        // 응답 DTO에 토큰을 담아서 반환
        return new AuthSignupResponseDto(bearerToken);
    }

    // 로그인 기능
    public AuthSigninResponseDto signin(AuthSigninRequestDto signinRequest) {
        // 이메일로 유저 찾기
        User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
                () -> new AuthException("가입되지 않은 유저입니다.")
        );

        // 입력된 비밀번호가 저장된 비밀번호와 일치하지 않으면 예외 발생
        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new AuthException("잘못된 비밀번호입니다.");
        }

        // 유저 정보를 바탕으로 JWT 토큰 생성
        String bearerToken = jwtUtil.createToken(user.getUserId(), user.getEmail(), user.getUserRole());

        // 응답 DTO에 토큰을 담아서 반환
        return new AuthSigninResponseDto(bearerToken);
    }
}
