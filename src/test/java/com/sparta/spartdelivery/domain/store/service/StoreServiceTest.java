package com.sparta.spartdelivery.domain.store.service;

import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.common.service.DateTImeService;
import com.sparta.spartdelivery.domain.menu.entity.Menu;
import com.sparta.spartdelivery.domain.menu.repository.MenuRepository;
import com.sparta.spartdelivery.domain.store.dto.request.StoreEditRequestDto;
import com.sparta.spartdelivery.domain.store.dto.request.StoreSaveRequestDto;
import com.sparta.spartdelivery.domain.store.dto.response.StoreEditResponseDto;
import com.sparta.spartdelivery.domain.store.dto.response.StoreFindResponseDto;
import com.sparta.spartdelivery.domain.store.dto.response.StoreSaveResponseDto;
import com.sparta.spartdelivery.domain.store.dto.response.StoresSearchResponseDto;
import com.sparta.spartdelivery.domain.store.entity.Store;
import com.sparta.spartdelivery.domain.store.exception.NotFoundStoreException;
import com.sparta.spartdelivery.domain.store.exception.PermissionDefinedOwnerException;
import com.sparta.spartdelivery.domain.store.exception.PermissionDefinedStoreUpdateException;
import com.sparta.spartdelivery.domain.store.exception.StoreNameIsExitsException;
import com.sparta.spartdelivery.domain.store.repository.StoreRepository;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private DateTImeService dateTImeService;

    @InjectMocks
    private StoreService storeService;

    private AuthUser authUser;
    private Store store;
    private StoreSaveRequestDto storeSaveRequestDto;
    private StoreEditRequestDto storeEditRequestDto;


    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);

        storeSaveRequestDto = new StoreSaveRequestDto("배떡", "10:00", "12:00", 12000);
        storeEditRequestDto = new StoreEditRequestDto("배떡 2호점", "12:00", "17:00", 15000);
        authUser = new AuthUser(1L, "오현택", UserRole.OWNER);


        // 수정테스트용
        store = new Store();
        store.setStoreId(1L);
        store.setStoreName("엽떡");
        store.setUserId(1L);
        store.setStatusShutdown(false);
        store.setStatusOpen(true);
    }



    // 상점 등록 테스트

    @Test
    void 상점_등록_성공케이스(){

        // Given
        LocalDateTime currentTime = LocalDateTime.now();
        Store newStore = Store.builder()
                .storeId(1L)
                .storeName(storeSaveRequestDto.getStoreName())
                .createdAt(currentTime)
                .build();

        when(dateTImeService.getCurrentDateTime(storeSaveRequestDto.getOpenTime()))
                .thenReturn(LocalTime.parse(storeSaveRequestDto.getOpenTime()));
        when(dateTImeService.getCurrentDateTime(storeSaveRequestDto.getCloseTime()))
                .thenReturn(LocalTime.parse(storeSaveRequestDto.getCloseTime()));
        when(storeRepository.save(any(Store.class))).thenReturn(newStore);

        // When
        StoreSaveResponseDto responseDto = storeService.saveStore(authUser, storeSaveRequestDto);

        // Then
        assertNotNull(responseDto);
        assertEquals(1L, responseDto.getStoreId());
        assertEquals(currentTime, responseDto.getCreatedAt());

    }

    @Test
    void 상점_등록_실패케이스(){

    }

    @Test
    void 상점_등록_실패케이스_유저가_사장권한이_없음(){
        // Given
        authUser = new AuthUser(1L, "김사장", UserRole.USER);  // 사장님이 아닌 경우

        // When & Then
        PermissionDefinedOwnerException exception = assertThrows(
                PermissionDefinedOwnerException.class,
                () -> storeService.saveStore(authUser, storeSaveRequestDto)
        );

        assertEquals("사장님 권한을 가진 유저만 가게를 만들 수 있습니다.", exception.getMessage());
    }

    @Test
    void 상점_등록_실패케이스_등록한가게의_갯수가_3개이상인경우(){

        given(storeRepository.findByUserId(authUser.getId())).willReturn(Collections.nCopies(3, new Store()));

        // When & Then
        PermissionDefinedOwnerException exception = assertThrows(
                PermissionDefinedOwnerException.class,
                () -> storeService.saveStore(authUser, storeSaveRequestDto)
        );

        assertEquals("사장님은 가게를 최대 3개까지만 운영할 수 있습니다.", exception.getMessage());

    }

    @Test
    void 상점_등록_실패케이스_상점명이_이미_존재함(){

        given(storeRepository.findByStoreName(storeSaveRequestDto.getStoreName())).willReturn(Collections.singleton(new Store()));


        // When & Then
        StoreNameIsExitsException exception = assertThrows(
                StoreNameIsExitsException.class,
                () -> storeService.saveStore(authUser, storeSaveRequestDto)
        );

        assertEquals("해당 이름을 가진 가게가 이미 존재합니다.", exception.getMessage());

    }





    // 상점 수정 테스트
    @Test
    void updateStore_ShouldThrowException_WhenUserHasNoPermission1() {

        given(storeRepository.findByStoreId(store.getStoreId())).willReturn(Optional.empty());

        // When & Then
        NotFoundStoreException exception = assertThrows(
                NotFoundStoreException.class,
                () -> storeService.updateStore(authUser, store.getStoreId(), storeEditRequestDto)
        );

        assertEquals("상점을 찾을 수 없습니다.", exception.getMessage());
    }



    @Test
    void updateStore_ShouldThrowException_WhenUserHasNoPermission() {

        given(storeRepository.findByStoreId(store.getStoreId())).willReturn(Optional.of(store));
        // Given: 다른 유저의 가게를 수정하려고 할 때
        store.setUserId(2L);  // 다른 유저의 가게

        // When & Then
        PermissionDefinedStoreUpdateException exception = assertThrows(
                PermissionDefinedStoreUpdateException.class,
                () -> storeService.updateStore(authUser, store.getStoreId(), storeEditRequestDto)
        );

        assertEquals("해당 가게에 대한 권한이 없습니다.", exception.getMessage());
    }

    @Test
    void updateStore_ShouldThrowException_WhenStoreNameAlreadyExists() {
        given(storeRepository.findByStoreId(store.getStoreId())).willReturn(Optional.of(store));
        // Given: 같은 이름의 가게가 이미 있을 때
        given(storeRepository.findByStoreName(storeEditRequestDto.getStoreName())).willReturn(Collections.singletonList(new Store()));

        // When & Then
        StoreNameIsExitsException exception = assertThrows(
                StoreNameIsExitsException.class,
                () -> storeService.updateStore(authUser, store.getStoreId(), storeEditRequestDto)
        );

        assertEquals("해당 이름을 가진 가게가 이미 존재합니다.", exception.getMessage());
    }

    @Test
    void updateStore_ShouldUpdateStoreSuccessfully() {
        given(storeRepository.findByStoreId(store.getStoreId())).willReturn(Optional.of(store));
        // Given: 정상적인 가게 수정
        given(storeRepository.findByStoreName(storeEditRequestDto.getStoreName()))
                .willReturn(Collections.emptyList());  // 이름 중복 없음

        given(storeRepository.findById(store.getStoreId())).willReturn(Optional.of(store));

        given(dateTImeService.getCurrentDateTime(storeEditRequestDto.getOpenTime()))
                .willReturn(LocalTime.parse(storeEditRequestDto.getOpenTime()));

        // 10:00
        given(dateTImeService.getCurrentDateTime(storeEditRequestDto.getCloseTime()))
                .willReturn(LocalTime.parse(storeEditRequestDto.getCloseTime()));
        // 12:00

        // When
        StoreEditResponseDto responseDto = storeService.updateStore(authUser, store.getStoreId(), storeEditRequestDto);

        // Then
        assertNotNull(responseDto);
        assertEquals("배떡 2호점", store.getStoreName());  // 이름이 변경되었는지 확인
        // 2024-03-01 10:00:00
        assertEquals(LocalTime.of(12, 0), store.getOpenTime());  // 오픈 시간 확인
        assertEquals(LocalTime.of(17, 0), store.getCloseTime());  // 마감 시간 확인
        assertEquals(15000, store.getMinOrderPrice());  // 최소 주문 가격 확인

    }





    // 조회

    @Test
    void findStoresByStoreName_ShouldReturnListOfStores() {

//        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        // Given: storeName이 "Test"와 일치하는 가게들
        List<Store> mockStores = new ArrayList<>();
        mockStores.add(store);  // Store 객체 추가
        Iterable<Store> iterableStores = mockStores;

        when(storeRepository.findByStoreNameLike("엽떡")).thenReturn((List<Store>) iterableStores);


        // When
        List<StoresSearchResponseDto> result = storeService.findStoresByStoreName("엽떡");

        // Then
        assertEquals(1, result.size());
        StoresSearchResponseDto dto = result.get(0);
        assertEquals(store.getStoreId(), dto.getStoreId());
        assertEquals(store.getStoreName(), dto.getStoreName());
        assertEquals(store.getOpenTime(), dto.getOpenTime());
        assertEquals(store.getCloseTime(), dto.getCloseTime());
        assertEquals(store.getMinOrderPrice(), dto.getMinOrderPrice());
    }

    @Test
    void findStoreByStoreId_ShouldReturnStoreWithMenuList() {

        when(storeRepository.findByStoreId(store.getStoreId())).thenReturn(Optional.of(store));

        // Given: storeId가 1인 Store와 해당 가게의 메뉴들
        List<Menu> mockMenus = new ArrayList<>();
        Menu menu1 = new Menu("Pizza", 10000, store.getStoreId(), 1L);
        mockMenus.add(menu1);

        when(menuRepository.findAllActiveByStoreId(store.getStoreId())).thenReturn(mockMenus);

        // When
        StoreFindResponseDto result = storeService.findStoreByStoreId(store.getStoreId());

        // Then
        assertEquals(store.getStoreId(), result.getStoreId());
        assertEquals(store.getStoreName(), result.getStoreName());
        assertEquals(store.getOpenTime(), result.getOpenTime());
        assertEquals(store.getCloseTime(), result.getCloseTime());
        assertEquals(store.getMinOrderPrice(), result.getMinOrderPrice());
        assertEquals(1, result.getMenuList().size());
        assertEquals(menu1.getMenuId(), result.getMenuList().get(0).getMenuId());
        assertEquals(menu1.getMenuName(), result.getMenuList().get(0).getMenuName());
    }

    @Test
    void findStoreByStoreId_ShouldThrowException_WhenStoreNotFound() {
        // Given: 존재하지 않는 storeId
        when(storeRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        NotFoundStoreException exception = assertThrows(NotFoundStoreException.class, () -> {
            storeService.findStoreByStoreId(1L);
        });

        assertEquals("상점을 찾을 수 없습니다.", exception.getMessage());
    }


    @Test
    void 상점폐업처리_성공케이스(){

        System.out.println(store);

        when(storeRepository.findByStoreId(store.getStoreId())).thenReturn(Optional.of(store));

        storeService.storeClose(authUser, store.getStoreId());

        // 폐업 처리면 true
        assertEquals(true, store.isStatusShutdown());

        // 가게 문을 닫았으니 fasle
        assertEquals(false, store.isStatusOpen());
    }

}