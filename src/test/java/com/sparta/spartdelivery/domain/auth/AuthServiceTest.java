package com.sparta.spartdelivery.domain.auth;

import com.sparta.spartdelivery.config.JwtUtil;
import com.sparta.spartdelivery.config.PasswordEncoder;
import com.sparta.spartdelivery.domain.auth.dto.request.AuthSignupRequestDto;
import com.sparta.spartdelivery.domain.auth.dto.response.AuthSignupResponseDto;
import com.sparta.spartdelivery.domain.auth.service.AuthService;
import com.sparta.spartdelivery.domain.user.entity.User;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import com.sparta.spartdelivery.domain.user.exception.UserException;
import com.sparta.spartdelivery.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    public void 회원가입_성공() {
        // given
        AuthSignupRequestDto requestDto = new AuthSignupRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setUsername("testuser");
        requestDto.setPassword("password123");
        requestDto.setUserRole("USER");

        String encodedPassword = "encodedPassword";

        // Mock 설정
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn(encodedPassword);
        when(userRepository.existsByEmail(requestDto.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(requestDto.getUsername())).thenReturn(false);

        // 새로운 User 객체를 저장한 후 반환하도록 설정
        User savedUser = new User(requestDto.getEmail(), requestDto.getUsername(), encodedPassword, UserRole.of(requestDto.getUserRole()));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // when
        AuthSignupResponseDto responseDto = authService.signup(requestDto);

        // then
        verify(userRepository).existsByEmail(requestDto.getEmail());
        verify(userRepository).existsByUsername(requestDto.getUsername());
        verify(userRepository).save(any(User.class)); // save 메서드가 호출되었는지 확인
    }

    @Test
    public void 회원가입_이메일이이미존재할때_InvalidRequestException발생() {
        // given
        AuthSignupRequestDto signupRequest = new AuthSignupRequestDto();
        signupRequest.setEmail("test@example.com");
        signupRequest.setUsername("testuser");
        signupRequest.setPassword("password123");
        signupRequest.setUserRole("USER");

        // Arrange
        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(UserException.class, () -> authService.signup(signupRequest));
    }
}
