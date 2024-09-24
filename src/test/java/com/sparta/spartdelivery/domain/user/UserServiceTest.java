package com.sparta.spartdelivery.domain.user;

import com.sparta.spartdelivery.domain.user.enums.UserRole;
import com.sparta.spartdelivery.config.PasswordEncoder;
import com.sparta.spartdelivery.domain.user.dto.request.UserChangePasswordRequestDto;
import com.sparta.spartdelivery.domain.user.entity.User;
import com.sparta.spartdelivery.domain.user.exception.AlreadyDeletedUserException;
import com.sparta.spartdelivery.domain.user.exception.UserInvalidPasswordException;
import com.sparta.spartdelivery.domain.user.exception.UserNotFoundException;
import com.sparta.spartdelivery.domain.user.repository.UserRepository;
import com.sparta.spartdelivery.domain.user.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    private User user; // User 객체 추가

    @BeforeEach
    public void setUp() {
        user = new User("test@example.com", "testUser", "encodedOldPassword", UserRole.USER);

    }

    @Test
    public void 사용자_존재_여부_확인() {
        long userId = 1L;
        UserChangePasswordRequestDto requestDto = new UserChangePasswordRequestDto("oldPassword", "newPassword123!");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.changePassword(userId, requestDto);
        });

        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage());
    }
    @Test
    public void 비밀번호_변경_성공() {
        long userId = 1L;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword123!";
        String encodedOldPassword = "encodedOldPassword"; // 기존 비밀번호 인코딩
        String encodedNewPassword = "encodedNewPassword123!"; // 새로운 비밀번호 인코딩

        UserChangePasswordRequestDto requestDto = new UserChangePasswordRequestDto(oldPassword, newPassword);

        // 사용자 설정
        user.changePassword(encodedOldPassword); // 사용자 비밀번호 설정
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(oldPassword, encodedOldPassword)).thenReturn(true);
        when(passwordEncoder.matches(newPassword, encodedOldPassword)).thenReturn(false); // 새 비밀번호가 기존 비밀번호와 같지 않음
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword); // 새로운 비밀번호 인코딩

        // 비밀번호 변경 메서드 호출
        userService.changePassword(userId, requestDto);

        // 비밀번호가 변경되었는지 확인
        assertEquals(encodedNewPassword, user.getPassword()); // 새로운 비밀번호가 인코딩된 형태로 저장되어야 함
    }

    @Test
    public void 사용자_삭제_사용자_존재하지않음() {
        long userId = 1L;
        String password = "somePassword";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(userId, password);
        });

        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    public void 사용자_삭제_비밀번호_불일치() {
        long userId = 1L;
        String password = "wrongPassword";
        String encodedPassword = "encodedCorrectPassword";

        user.changePassword(encodedPassword); // 사용자 비밀번호 설정

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false); // 비밀번호 불일치

        UserInvalidPasswordException exception = assertThrows(UserInvalidPasswordException.class, () -> {
            userService.deleteUser(userId, password);
        });

        assertEquals("잘못된 비밀번호입니다.", exception.getMessage());
    }

    @Test
    public void 사용자_삭제_이미_탈퇴된_사용자() {
        long userId = 1L;
        String password = "correctPassword";
        String encodedPassword = "encodedCorrectPassword";

        user.changePassword(encodedPassword); // 사용자 비밀번호 설정
        user.markAsDeleted(); // 사용자를 삭제 상태로 설정

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true); // 비밀번호 일치

        AlreadyDeletedUserException exception = assertThrows(AlreadyDeletedUserException.class, () -> {
            userService.deleteUser(userId, password);
        });

        assertEquals("이미 탈퇴한 사용자입니다.", exception.getMessage());
    }

    @Test
    public void 사용자_삭제_성공() {
        long userId = 1L;
        String password = "correctPassword";
        String encodedPassword = "encodedCorrectPassword"; // 인코딩된 비밀번호

        user.changePassword(encodedPassword); // 사용자 비밀번호 설정
        // 사용자가 삭제되지 않은 상태로 설정
        // (markAsDeleted() 호출 시 인자를 전달할 필요 없음)

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true); // 비밀번호 일치

        // 사용자 삭제 메서드 호출
        userService.deleteUser(userId, password);

        // 사용자가 삭제되었는지 확인
        assertTrue(user.isDeleted()); // 사용자가 삭제 상태로 변경되었는지 확인
    }
}