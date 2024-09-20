package com.sparta.spartdelivery.domain.user.controller;

import com.sparta.spartdelivery.common.annotation.Auth;
import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
import com.sparta.spartdelivery.domain.user.dto.request.UserDeleteRequestDto;
import com.sparta.spartdelivery.domain.user.dto.request.UserChangePasswordRequestDto;
import com.sparta.spartdelivery.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    // 비밀번호 변경
    @PutMapping("/users/pwdforget")
    public ResponseEntity<CommonResponseDto<Void>> changePassword(
            @Auth AuthUser authUser,
            @RequestBody UserChangePasswordRequestDto userChangePasswordRequest) {

        // 비밀번호 변경 로직 호출
        userService.changePassword(authUser.getId(), userChangePasswordRequest);

        // 성공 응답 생성
        CommonResponseDto<Void> responseDto = new CommonResponseDto<>(
                HttpStatus.OK,
                "비밀번호가 성공적으로 변경되었습니다.",
                null // 데이터가 필요 없으므로 null
        );

        // ResponseEntity 반환
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 비밀번호를 사용하여 사용자 탈퇴
    @DeleteMapping("/users/exit")
    public ResponseEntity<CommonResponseDto<Void>> deleteUser(
            @PathVariable long userId,
            @RequestBody UserDeleteRequestDto deleteUserRequest) {

        // 사용자 삭제 서비스 호출
        userService.deleteUser(userId, deleteUserRequest.getPassword());

        // 성공 응답 생성
        CommonResponseDto<Void> responseDto = new CommonResponseDto<>(
                HttpStatus.OK,
                "회원 탈퇴가 성공적으로 완료되었습니다.",
                null // 데이터가 필요 없으므로 null
        );

        // ResponseEntity 반환
        return new ResponseEntity<>(responseDto, HttpStatus.OK);    }
}