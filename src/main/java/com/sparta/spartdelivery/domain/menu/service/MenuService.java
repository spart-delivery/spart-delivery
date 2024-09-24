package com.sparta.spartdelivery.domain.menu.service;

import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.domain.menu.entity.Menu;
import com.sparta.spartdelivery.domain.menu.exception.*;
import com.sparta.spartdelivery.domain.menu.repository.MenuRepository;
import com.sparta.spartdelivery.domain.menu.dto.request.MenuSaveRequestDto;
import com.sparta.spartdelivery.domain.menu.dto.request.MenuUpdateRequestDto;
import com.sparta.spartdelivery.domain.menu.dto.response.MenuSaveResponseDto;
import com.sparta.spartdelivery.domain.menu.dto.response.MenuUpdateResponseDto;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
            throw new MenuExistsException(HttpStatus.ALREADY_REPORTED,"이미 존재하는 메뉴입니다."); // 커스텀exception
        }
        Menu newMenu = new Menu(
                menuSaveRequestDto.getMenuName(),
                menuSaveRequestDto.getMenuPrice(),
                storeId,
                menuSaveRequestDto.getCategoryId());
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
        Menu menu = menuRepository.findByMenuId(menuId)
                .orElseThrow(
                        () -> new MenuIdNotFoundException(HttpStatus.NOT_FOUND, "menuId가 없습니다."));
        menu.update(
                menuUpdateRequestDto.getMenuName(),
                menuUpdateRequestDto.getMenuPrice());
        return new MenuUpdateResponseDto(
                menu.getMenuName(),
                menu.getMenuPrice());
    }

    /* 삭제 */
    @Transactional
    public void deleteMenu(Long menuId, AuthUser authUser) {


        /* 메뉴 찾기*/
        Menu menu = menuRepository.findByMenuId(menuId)
                .orElseThrow(
                        ()-> new MenuIdNotFoundException(HttpStatus.NOT_FOUND, "삭제할 menuId가 없습니다."));
        menu.withdraw();

        /* 메뉴 권한 검증 */
        validateOwnerMenu(authUser, menuId, menu.getStoreId());

        /* OWNER 인지 확인 */
        if (!authUser.getUserRole().equals(UserRole.OWNER)) {
            throw new AuthUserDefinedException(HttpStatus.UNAUTHORIZED, "유효하지 않은 사용자입니다.");
        }

        /* withdraw 상태로 변경후 저장 */
        menu.withdraw();
        menuRepository.save(menu);
    }

    public Menu findMenuById(Long menuId) {
        return menuRepository.findByMenuId(menuId)
                .orElseThrow(() -> new MenuIdNotFoundException(HttpStatus.NOT_FOUND,"메뉴를 찾을 수 없습니다.")); // exception 고치기
    }


    /* 사장인지 검증 */
    public void validateOwner(AuthUser authUser) {
        if (!authUser.getUserRole().equals(UserRole.OWNER)) {
            throw new OwnerDefinedException(HttpStatus.UNAUTHORIZED,"사장님만 접근할 수 있습니다.");
        }
    }

    public void validateOwnerMenu(AuthUser authUser, Long menuId, Long storeId) {
        Menu menu = findMenuById(menuId);
        if (!authUser.getUserRole().equals(UserRole.OWNER) || !menu.getStoreId().equals(storeId)) {
            throw new StoreOwnerDefinedException(HttpStatus.UNAUTHORIZED,"해당 메뉴의 사장님만 수정/삭제할 수 있습니다.");
        }
    }


}
