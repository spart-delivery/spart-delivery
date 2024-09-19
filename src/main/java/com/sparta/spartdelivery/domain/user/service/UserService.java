package com.sparta.spartdelivery.domain.user.service;


import com.sparta.spartdelivery.config.PasswordEncoder;
import com.sparta.spartdelivery.domain.user.dto.request.UserChangePasswordRequest;
import com.sparta.spartdelivery.domain.user.dto.response.UserResponse;
import com.sparta.spartdelivery.domain.user.entity.User;
import com.sparta.spartdelivery.domain.user.exception.InvalidRequestException;
import com.sparta.spartdelivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));
        return new UserResponse(user.getId(), user.getEmail());
    }

    @Transactional
    public void changePassword(long userId, UserChangePasswordRequest userChangePasswordRequest) {
        if (userChangePasswordRequest.getNewPassword().length() < 8 ||
                !userChangePasswordRequest.getNewPassword().matches(".*\\d.*") ||
                !userChangePasswordRequest.getNewPassword().matches(".*[A-Z].*")) {
            throw new InvalidRequestException("새 비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidRequestException("User not found"));

        if (passwordEncoder.matches(userChangePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new InvalidRequestException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
        }

        if (!passwordEncoder.matches(userChangePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new InvalidRequestException("잘못된 비밀번호입니다.");
        }

        user.changePassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
    }

    // 비밀번호 확인 및 회원 탈퇴
    @Transactional
    public void deleteUser(long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidRequestException("사용자를 찾을 수 없습니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidRequestException("비밀번호가 일치하지 않습니다.");
        }

        // 이미 탈퇴된 사용자일 경우
        if (user.isDeleted()) {
            throw new InvalidRequestException("이미 탈퇴한 사용자입니다.");
        }

        // 사용자 삭제 처리
        userRepository.delete(user);
    }
}
