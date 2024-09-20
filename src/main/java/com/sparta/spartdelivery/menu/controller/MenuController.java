package com.sparta.spartdelivery.menu.controller;

import com.sparta.spartdelivery.menu.dto.request.MenuSaveRequestDto;
import com.sparta.spartdelivery.menu.dto.request.MenuUpdateRequestDto;
import com.sparta.spartdelivery.menu.dto.response.MenuSaveResponseDto;
import com.sparta.spartdelivery.menu.dto.response.MenuUpdateResponseDto;
import com.sparta.spartdelivery.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    /* 메뉴 생성 */
    @PostMapping("/menu/signup")
    public ResponseEntity<MenuSaveResponseDto> saveMenu(@RequestBody MenuSaveRequestDto menuSaveRequestDto){
        return ResponseEntity.ok(menuService.saveMenu(menuSaveRequestDto));
    }
    /* 메뉴 수정 */
    @PutMapping("/menu/{menuId}")
    public ResponseEntity<MenuUpdateResponseDto> updateMenu(@PathVariable Long menuId, @RequestBody MenuUpdateRequestDto menuUpdateRequestDto){
        return ResponseEntity.ok(menuService.updateMenu(menuId,menuUpdateRequestDto));
    }
    /* 메뉴 삭제 */
    @DeleteMapping("/menu/{menuId}")
    public void deleteMenu(@PathVariable Long menuId){
        menuService.deleteMenu(menuId);
    }

}
