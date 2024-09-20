package com.sparta.spartdelivery.domain.menu.service;

import com.sparta.spartdelivery.domain.menu.entity.Menu;
import com.sparta.spartdelivery.domain.menu.repository.MenuRepository;
import com.sparta.spartdelivery.domain.menu.dto.request.MenuSaveRequestDto;
import com.sparta.spartdelivery.domain.menu.dto.request.MenuUpdateRequestDto;
import com.sparta.spartdelivery.domain.menu.dto.response.MenuSaveResponseDto;
import com.sparta.spartdelivery.domain.menu.dto.response.MenuUpdateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository menuRepository;

    /* 생성 */
    @Transactional
    public MenuSaveResponseDto saveMenu(MenuSaveRequestDto menuSaveRequestDto) {
        Menu newMenu = new Menu(
                menuSaveRequestDto.getMenuName(),
                menuSaveRequestDto.getMenuPrice(),
                menuSaveRequestDto.getStoreId());
        Menu savedMenu = menuRepository.save(newMenu);

        return new MenuSaveResponseDto(
                savedMenu.getStoreId(),
                savedMenu.getMenuId(),
                savedMenu.getMenuName(),
                savedMenu.getMenuPrice());
    }

    /* 수정 */
    @Transactional
    public MenuUpdateResponseDto updateMenu(Long menuId, MenuUpdateRequestDto menuUpdateRequestDto) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(
                        ()-> new NullPointerException("menuId가 없습니다."));
        menu.update(
                menuUpdateRequestDto.getMenuName(),
                menuUpdateRequestDto.getMenuPrice(),
                menuUpdateRequestDto.getMenuId());
        return new MenuUpdateResponseDto(
                menu.getMenuName(),
                menu.getMenuPrice());
    }

    /* 삭제 */
    @Transactional
    public void deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(
                        ()-> new NullPointerException("삭제할 menuId가 없습니다."));
        menu.withdraw();
    }

    public Menu findMenuById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NullPointerException("메뉴를 찾을 수 없습니다."));
    }
}
