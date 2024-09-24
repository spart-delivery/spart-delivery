package com.sparta.spartdelivery.domain.user.service;

import com.sparta.spartdelivery.config.PasswordEncoder;
import com.sparta.spartdelivery.domain.user.dto.request.UserChangePasswordRequestDto;
import com.sparta.spartdelivery.domain.user.entity.User;
import com.sparta.spartdelivery.domain.user.exception.AlreadyDeletedUserException;
import com.sparta.spartdelivery.domain.user.exception.SamePasswordException;
import com.sparta.spartdelivery.domain.user.exception.UserNotFoundException;
import com.sparta.spartdelivery.domain.user.exception.UserInvalidPasswordException;
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

    @Transactional
    //비밀번호 변경
    public void changePassword(long userId, UserChangePasswordRequestDto userChangePasswordRequest) {
        // 사용자 존재 여부 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        // 새 비밀번호가 기존 비밀번호와 동일한지 확인
        if (passwordEncoder.matches(userChangePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new SamePasswordException();
        }
        // 기존 비밀번호 확인
        if (!passwordEncoder.matches(userChangePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new UserInvalidPasswordException();
        }
        // 비밀번호 변경
        user.changePassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
    }

    // 비밀번호 확인 및 회원 탈퇴
    @Transactional
    public void deleteUser(long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserInvalidPasswordException();
        }

        // 이미 탈퇴된 사용자일 경우
        if (user.isDeleted()) {
            throw new AlreadyDeletedUserException();
        }

        // 사용자 삭제 처리
        user.markAsDeleted(); // 삭제 상태로 변경
        userRepository.save(user); // 변경된 상태 저장
    }
}
