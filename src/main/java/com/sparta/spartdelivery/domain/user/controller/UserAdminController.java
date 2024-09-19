package com.sparta.spartdelivery.domain.user.controller;

import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
import com.sparta.spartdelivery.domain.user.dto.request.UserRoleChangeRequest;
import com.sparta.spartdelivery.domain.user.service.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAdminController {

    private final UserAdminService userAdminService;

    // 관리자 역할 변경
//    @PatchMapping("/owner/users/{userId}")
//    public void changeUserRole(@PathVariable long userId, @RequestBody UserRoleChangeRequest userRoleChangeRequest) {
//        userAdminService.changeUserRole(userId, userRoleChangeRequest);
//    }

    @PatchMapping("/owner/users/{userId}")
    public ResponseEntity<CommonResponseDto<Void>> changeUserRole(
            @PathVariable long userId,
            @RequestBody UserRoleChangeRequest userRoleChangeRequest) {

        userAdminService.changeUserRole(userId, userRoleChangeRequest);

        // 성공 응답 생성
        CommonResponseDto<Void> responseDto = new CommonResponseDto<>(
                HttpStatus.OK,
                "User role updated successfully",
                null
        );

        return ResponseEntity.ok(responseDto);
    }
}
