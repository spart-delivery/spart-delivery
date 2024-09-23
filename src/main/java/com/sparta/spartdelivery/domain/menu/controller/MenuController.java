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
        menuService.validateOwner(authUser);
        MenuSaveResponseDto menuSaveResponseDto = menuService.saveMenu(menuSaveRequestDto,storeId);
        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success",menuSaveResponseDto),HttpStatus.OK);
    }

    /* 메뉴 수정 */
    @PutMapping("/menu/{menuId}")
    public ResponseEntity<CommonResponseDto<MenuUpdateResponseDto>> updateMenu(
            @Auth AuthUser authUser,
            @PathVariable Long menuId,
            @RequestBody MenuUpdateRequestDto menuUpdateRequestDto) {
        Menu menu = menuService.findMenuById(menuId); // 메뉴 정보 가져오기
        Long storeId = menu.getStoreId(); // 메뉴에서 storeId 가져오기
        menuService.validateOwnerMenu(authUser, menuId, storeId); // 사장님인지 확인 및 메뉴 가게 소속 확인
        MenuUpdateResponseDto menuUpdateResponseDto = menuService.updateMenu(menuId, menuUpdateRequestDto);
        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", menuUpdateResponseDto), HttpStatus.OK);
    }

    /* 메뉴 삭제 */
    @DeleteMapping("/menu/{menuId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteMenu(
            @Auth AuthUser authUser,
            @PathVariable Long menuId) {
        Menu menu = menuService.findMenuById(menuId);
        Long storeId = menu.getStoreId();
        menuService.validateOwnerMenu(authUser, menuId, storeId);
        menuService.deleteMenu(menuId);
        CommonResponseDto<Void> deleteResponseDto = new CommonResponseDto<>(HttpStatus.OK, "success",null);
        return ResponseEntity.ok(deleteResponseDto);
    }


}
