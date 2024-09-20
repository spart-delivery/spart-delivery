package com.sparta.spartdelivery.domain.menu.controller;

import com.sparta.spartdelivery.common.annotation.Auth;
import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
import com.sparta.spartdelivery.domain.menu.entity.Menu;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import com.sparta.spartdelivery.domain.menu.dto.request.MenuSaveRequestDto;
import com.sparta.spartdelivery.domain.menu.dto.request.MenuUpdateRequestDto;
import com.sparta.spartdelivery.domain.menu.dto.response.MenuSaveResponseDto;
import com.sparta.spartdelivery.domain.menu.dto.response.MenuUpdateResponseDto;
import com.sparta.spartdelivery.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    /* 메뉴 생성 */
    @PostMapping("/stores/{storeId}/menu")
    public ResponseEntity<CommonResponseDto<MenuSaveResponseDto>> saveMenu(
            @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @RequestBody MenuSaveRequestDto menuSaveRequestDto) {
        validateOwner(authUser); // 사장님인지 확인
        MenuSaveResponseDto menuSaveResponseDto = menuService.saveMenu(menuSaveRequestDto);
        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success",menuSaveResponseDto),HttpStatus.OK);
    }

    /* 메뉴 수정 */
    @PutMapping("/menu/{menuId}")
    public ResponseEntity<CommonResponseDto<MenuUpdateResponseDto>> updateMenu(
            @Auth AuthUser authUser,
            @PathVariable Long menuId,
            @RequestBody MenuUpdateRequestDto menuUpdateRequestDto) {
        validateOwnerMenu(authUser, menuId); // 사장님인지 확인 및 메뉴 소속 확인
        MenuUpdateResponseDto menuUpdateResponseDto = menuService.updateMenu(menuId, menuUpdateRequestDto);
        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", menuUpdateResponseDto), HttpStatus.OK);
    }

    /* 메뉴 삭제 */
    @DeleteMapping("/menu/{menuId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteMenu(
            @Auth AuthUser authUser,
            @PathVariable Long menuId) {
        validateOwnerMenu(authUser, menuId); // 사장님인지 확인 및 메뉴 소속 확인
        menuService.deleteMenu(menuId);
        CommonResponseDto<Void> deleteResponseDto = new CommonResponseDto<>(HttpStatus.OK, "success",null);
        return ResponseEntity.ok(deleteResponseDto);
    }

    /* 사장인지 확인 */
    private void validateOwner(AuthUser authUser) {
        if (!authUser.getUserRole().equals(UserRole.OWNER)) {
            throw new IllegalArgumentException("사장님만 접근할 수 있습니다.");
        }
    }

    private void validateOwnerMenu(AuthUser authUser, Long menuId) {
        Menu menu = menuService.findMenuById(menuId);
        if (!authUser.getUserRole().equals(UserRole.OWNER) || !menu.getStoreId().equals(menu.getStoreId())) {
            throw new IllegalArgumentException("해당 메뉴의 사장님만 수정/삭제할 수 있습니다.");
        }
    }
}
