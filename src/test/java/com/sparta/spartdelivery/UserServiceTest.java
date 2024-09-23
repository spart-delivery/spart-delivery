package com.sparta.spartdelivery;

import com.sparta.spartdelivery.config.PasswordEncoder;
import com.sparta.spartdelivery.domain.user.dto.request.UserChangePasswordRequestDto;
import com.sparta.spartdelivery.domain.user.entity.User;
import com.sparta.spartdelivery.domain.user.exception.InvalidRequestException;
import com.sparta.spartdelivery.domain.user.repository.UserRepository;
import com.sparta.spartdelivery.domain.user.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;


    @Test
    public void 사용자_확인() {
        long userId = 1L;
        UserChangePasswordRequestDto requestDto = new UserChangePasswordRequestDto("oldPassword", "newPassword123!");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            userService.changePassword(userId, requestDto);
        });

        assertEquals("User not found", exception.getMessage());
    }

}