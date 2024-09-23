package com.sparta.spartdelivery.domain.service.menu;

import com.sparta.spartdelivery.domain.menu.dto.request.MenuSaveRequestDto;
import com.sparta.spartdelivery.domain.menu.dto.request.MenuUpdateRequestDto;
import com.sparta.spartdelivery.domain.menu.dto.response.MenuSaveResponseDto;
import com.sparta.spartdelivery.domain.menu.dto.response.MenuUpdateResponseDto;
import com.sparta.spartdelivery.domain.menu.entity.Menu;
import com.sparta.spartdelivery.domain.menu.entity.MenuStatus;
import com.sparta.spartdelivery.domain.menu.repository.MenuRepository;
import com.sparta.spartdelivery.domain.menu.service.MenuService;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import com.sparta.spartdelivery.common.dto.AuthUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    /* 가짜 객체를 사용하여 테스트 */
    @Mock
    private MenuRepository menuRepository;

    private AuthUser authUser;

    @BeforeEach  /* 각각의 테스트 전에 실행 */
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authUser = new AuthUser(1L, "testUser", UserRole.OWNER);
    }

    /* 메뉴저장성공 테스트 */
    @Test
    public void saveMenu_Success() {

        /* given
        * 가게 이름과, 메뉴를 검색했을 때 메뉴 존재하지 않도록 설정
        * save가 호출 될 때 메뉴를 반환 */
        MenuSaveRequestDto requestDto = new MenuSaveRequestDto("Pizza", 10000);
        when(menuRepository.findByStoreIdAndMenuName(1L, "Pizza")).thenReturn(Optional.empty());
        when(menuRepository.save(any(Menu.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        /* when
         saveMenu를 호출, 메뉴 저장 (Pizza, 10000)*/
        MenuSaveResponseDto response = menuService.saveMenu(requestDto, 1L);

        /* then
        메뉴 이름, 가격이 맞는지 확인 */
        assertEquals("Pizza", response.getMenuName());
        assertEquals(10000, response.getMenuPrice());
    }

    /* 메뉴가 이미 존재하는지 테스트 */
    @Test
    public void saveMenu_MenuAlreadyExists() {

        /* given
        1번 가게에 피자,가격10000원 이라는 메뉴 생성
        1번 가게에 Pizza를 검색 했을 때 -> 기존 메뉴 반환함 */
        Menu existingMenu = new Menu("Pizza", 10000, 1L);
        when(menuRepository.findByStoreIdAndMenuName(1L, "Pizza")).thenReturn(Optional.of(existingMenu));

        /* when
        Pizza와 가격10000원 데이터 생성
        /* 메뉴를 저장할 때 이미 똑같은 메뉴가 존재한다면 예외발생하는지 확인 */
        MenuSaveRequestDto requestDto = new MenuSaveRequestDto("Pizza", 10000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            menuService.saveMenu(requestDto, 1L);
        });

        /* then */
        assertEquals("이미 존재하는 메뉴입니다.", exception.getMessage());
    }

    /* 메뉴 업데이트 성공 테스트 */
    @Test
    public void updateMenu_Success() {
        /* given
        업데이트 할 기존의 메뉴 생성 (메뉴이름, 가격, 가게id)
        찾는 가게의 기존메뉴 반환
        업데이트 할 메뉴의 생성 (이름 ,가격)*/
        Menu existingMenu = new Menu("Pizza", 10000, 1L);
        existingMenu.setMenuId(1L);
        when(menuRepository.findById(1L)).thenReturn(Optional.of(existingMenu));
        MenuUpdateRequestDto updateRequestDto = new MenuUpdateRequestDto("UpdatePizza", 12000);

        /* when
        기존 메뉴의 값 변경 (이름, 가격)
        메서드 호출해서 값이 변경된 메뉴로 업데이트 */
        existingMenu.update(updateRequestDto.getMenuName(), updateRequestDto.getMenuPrice());
        MenuUpdateResponseDto response = menuService.updateMenu(1L, updateRequestDto);

        /* then
        업데이트 한 값이 맞는지 확인 */
        assertEquals("UpdatePizza", response.getMenuName());
        assertEquals(12000, response.getMenuPrice());
    }

    /* 업데이트 할 메뉴가 없는 경우 테스트*/
    @Test

    public void updateMenu_MenuNotFound() {
        /* given
        빈 Optinal을 반환
        업데이트 할 메뉴 dto 새엇ㅇ ( 이름, 가격 ) */
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        MenuUpdateRequestDto updateRequestDto = new MenuUpdateRequestDto("UpdatePizza", 12000);

        /* when
         메뉴가 존재하지 않을때 예외발생하는지 확인 */
        Exception exception = assertThrows(NullPointerException.class, () -> {
            menuService.updateMenu(1L, updateRequestDto);
        });

        /* then
        생각한 예외발생이 맞는지 확인 */
        assertEquals("menuId가 없습니다.", exception.getMessage());
    }

    /* 메뉴삭제가 성공적으로 됐을 때 */
    @Test
    public void deleteMenu_Success() {
        /* given
        *   */
        Menu existingMenu = new Menu("Pizza", 10000, 1L);
        when(menuRepository.findById(1L)).thenReturn(Optional.of(existingMenu));

        /* when
        * 메뉴1번 삭제요청 */
        menuService.deleteMenu(1L,authUser);

        /* then
        메뉴의 상태가 WITHDRAWN으로 변경되어야 함
        save 메서드를 호출해서 상태가 변경됐는지 확인 */
        assertEquals(MenuStatus.WITHDRAWN, existingMenu.getStatus());
        verify(menuRepository, times(1)).save(existingMenu);
    }

    /* 메뉴를 삭제 할 때 없는 메뉴인 경우 */
    @Test
    public void deleteMenu_MenuNotFound() {
        /* given
         메뉴가 존재하지 않을 때  */
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        /* when
        * 메뉴를 삭제 할 때, 메뉴가 없으면 예외발생 */

        Exception exception = assertThrows(NullPointerException.class, () -> {
            menuService.deleteMenu(1L, authUser);
        });

        /* then
        *  예상한 예외메시지가 나오는지 확인 */
        assertEquals("삭제할 menuId가 없습니다.", exception.getMessage());
    }

    /* 사장님이 아닌 사람이 삭제하는경우 */
    @Test
    public void deleteMenu_NotOwner() {
        /* given
        *  삭제 할 메뉴는 있지만 삭제하는 사람이 owner가 아닌 경우 */
        Menu existingMenu = new Menu("Pizza", 10000, 1L);
        AuthUser notOwnerUser = new AuthUser(2L, "otherUser", UserRole.USER); // owner가 아닌 일반사용자
        when(menuRepository.findById(1L)).thenReturn(Optional.of(existingMenu));

        /* when
        * owner가 아닌 사용자가 삭제 요청할때 예외메세지 반환 */
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            menuService.deleteMenu(1L, notOwnerUser);
        });

        /* then
        * 예상한 예상메시지 확인  */
        assertEquals("해당 메뉴의 사장님만 수정/삭제할 수 있습니다.", exception.getMessage());
    }

}
