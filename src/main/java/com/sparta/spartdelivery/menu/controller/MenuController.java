package com.sparta.spartdelivery.menu.controller;

import com.sparta.spartdelivery.common.annotation.Auth;
import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import com.sparta.spartdelivery.menu.dto.request.MenuSaveRequestDto;
import com.sparta.spartdelivery.menu.dto.request.MenuUpdateRequestDto;
import com.sparta.spartdelivery.menu.dto.response.MenuSaveResponseDto;
import com.sparta.spartdelivery.menu.dto.response.MenuUpdateResponseDto;
import com.sparta.spartdelivery.menu.entity.Menu;
import com.sparta.spartdelivery.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    /* 메뉴 생성 */
    @PostMapping("/stores/{storeId}/menu")
    public ResponseEntity<MenuSaveResponseDto> saveMenu(@Auth AuthUser authUser, @PathVariable Long storeId, @RequestBody MenuSaveRequestDto menuSaveRequestDto) {
        validateOwner(authUser); // 사장님인지 확인
        return ResponseEntity.ok(menuService.saveMenu(menuSaveRequestDto));
    }

    /* 메뉴 수정 */
    @PutMapping("/menu/{menuId}")
    public ResponseEntity<MenuUpdateResponseDto> updateMenu(@Auth AuthUser authUser, @PathVariable Long menuId, @RequestBody MenuUpdateRequestDto menuUpdateRequestDto) {
        validateOwnerMenu(authUser, menuId); // 사장님인지 확인 및 메뉴 소속 확인
        return ResponseEntity.ok(menuService.updateMenu(menuId, menuUpdateRequestDto));
    }

    /* 메뉴 삭제 */
    @DeleteMapping("/menu/{menuId}")
    public void deleteMenu(@Auth AuthUser authUser, @PathVariable Long menuId) {
        validateOwnerMenu(authUser, menuId); // 사장님인지 확인 및 메뉴 소속 확인
        menuService.deleteMenu(menuId);
    }

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
