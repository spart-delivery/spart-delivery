package com.sparta.spartdelivery.domain.menu.service;

import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.domain.menu.entity.Menu;
import com.sparta.spartdelivery.domain.menu.repository.MenuRepository;
import com.sparta.spartdelivery.domain.menu.dto.request.MenuSaveRequestDto;
import com.sparta.spartdelivery.domain.menu.dto.request.MenuUpdateRequestDto;
import com.sparta.spartdelivery.domain.menu.dto.response.MenuSaveResponseDto;
import com.sparta.spartdelivery.domain.menu.dto.response.MenuUpdateResponseDto;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
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
    public MenuSaveResponseDto saveMenu(MenuSaveRequestDto menuSaveRequestDto, Long storeId) {
        /* 생성 시 메뉴 중복 체크 */
        if (menuRepository.findByStoreIdAndMenuName(storeId, menuSaveRequestDto.getMenuName()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 메뉴입니다.");
        }
        Menu newMenu = new Menu(
                menuSaveRequestDto.getMenuName(),
                menuSaveRequestDto.getMenuPrice(),
                storeId);
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
                        () -> new NullPointerException("menuId가 없습니다."));
        menu.update(
                menuUpdateRequestDto.getMenuName(),
                menuUpdateRequestDto.getMenuPrice());
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

    /* 사장인지 검증 */
    public void validateOwner(AuthUser authUser) {
        if (!authUser.getUserRole().equals(UserRole.OWNER)) {
            throw new IllegalArgumentException("사장님만 접근할 수 있습니다.");
        }
    }

    public void validateOwnerMenu(AuthUser authUser, Long menuId, Long storeId) {
        Menu menu = findMenuById(menuId);
        if (!authUser.getUserRole().equals(UserRole.OWNER) || !menu.getStoreId().equals(storeId)) {
            throw new IllegalArgumentException("해당 메뉴의 사장님만 수정/삭제할 수 있습니다.");
        }
    }
}
