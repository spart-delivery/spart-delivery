package com.sparta.spartdelivery.menu.service;

import com.sparta.spartdelivery.menu.dto.request.MenuSaveRequestDto;
import com.sparta.spartdelivery.menu.dto.request.MenuUpdateRequestDto;
import com.sparta.spartdelivery.menu.dto.response.MenuSaveResponseDto;
import com.sparta.spartdelivery.menu.dto.response.MenuUpdateResponseDto;
import com.sparta.spartdelivery.menu.entity.Menu;
import com.sparta.spartdelivery.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository menuRepository;

    @Transactional
    public MenuSaveResponseDto saveMenu(MenuSaveRequestDto menuSaveRequestDto) {
        Menu newMenu = new Menu(
                menuSaveRequestDto.getMenuName(),
                menuSaveRequestDto.getMenuPrice());
        Menu savedMenu = menuRepository.save(newMenu);

        return new MenuSaveResponseDto(
                savedMenu.getStoreId(),
                savedMenu.getMenuId(),
                savedMenu.getMenuName(),
                savedMenu.getMenuPrice());
    }

    @Transactional
    public MenuUpdateResponseDto updateMenu(Long menuId, MenuUpdateRequestDto menuUpdateRequestDto) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(()-> new NullPointerException("menuId가 없습니다."));
        menu.update(
                menuUpdateRequestDto.getMenuName(),
                menuUpdateRequestDto.getMenuPrice());
        return new MenuUpdateResponseDto(
                menu.getMenuName(),
                menu.getMenuPrice());
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(()-> new NullPointerException("삭제할 menuId가 없습니다."));
        menu.withdraw();
    }

    public Menu findMenuById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));
    }
}
