package com.sparta.spartdelivery.domain.store.service;

import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.common.service.DateTImeService;
import com.sparta.spartdelivery.domain.menu.entity.Menu;
import com.sparta.spartdelivery.domain.menu.repository.MenuRepository;
import com.sparta.spartdelivery.domain.store.dto.request.StoreEditRequestDto;
import com.sparta.spartdelivery.domain.store.dto.request.StoreSaveRequestDto;
import com.sparta.spartdelivery.domain.store.dto.request.StoreStatisticsRequestDto;
import com.sparta.spartdelivery.domain.store.dto.response.*;
import com.sparta.spartdelivery.domain.store.entity.Store;
import com.sparta.spartdelivery.domain.store.exception.*;
import com.sparta.spartdelivery.domain.store.repository.StoreRepository;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    private final MenuRepository menuRepository;

    private final DateTImeService dateTImeService;

    public StoreSaveResponseDto saveStore(AuthUser authUser, StoreSaveRequestDto storeSaveRequestDto) {

        if(!authUser.getUserRole().equals(UserRole.OWNER)){
            throw new PermissionDefinedOwnerException(HttpStatus.UNAUTHORIZED, "사장님 권한을 가진 유저만 가게를 만들 수 있습니다.");
        }

        if(storeRepository.findByUserId(authUser.getId()).size() >= 3){
            throw new PermissionDefinedOwnerException(HttpStatus.BAD_REQUEST, "사장님은 가게를 최대 3개까지만 운영할 수 있습니다.");
        }

        if(StreamSupport.stream(storeRepository.findByStoreName(storeSaveRequestDto.getStoreName()).spliterator(), false).findAny().isPresent()){
            throw new StoreNameIsExitsException(HttpStatus.BAD_REQUEST, "해당 이름을 가진 가게가 이미 존재합니다.");
        }

        try {

            Store newStore = Store.builder()
                    .storeName(storeSaveRequestDto.getStoreName())
                    .openTime(dateTImeService.getCurrentDateTime(storeSaveRequestDto.getOpenTime()))
                    .closeTime(dateTImeService.getCurrentDateTime(storeSaveRequestDto.getCloseTime()))
                    .minOrderPrice(storeSaveRequestDto.getMinOrderPrice())
                    .userId(authUser.getId())
                    .build();

            Store saveStore = storeRepository.save(newStore);

            return StoreSaveResponseDto.builder()
                    .storeId(saveStore.getStoreId())
                    .createdAt(saveStore.getCreatedAt())
                    .build();
        } catch (RuntimeException e){
            throw new RuntimeException("상점 생성도중 알수 없는 에러가 발생하였습니다.");
        }

    }

    @Transactional
    public StoreEditResponseDto updateStore(AuthUser authUser, Long storeId, StoreEditRequestDto storeEditRequestDto) {

        Store store = getStore(storeId);

        // 권한 체크
        checkPermission(authUser.getId(), store.getUserId());

        // 이름 중복 체크
        if(!store.getStoreName().equals(storeEditRequestDto.getStoreName()) && StreamSupport.stream(storeRepository.findByStoreName(storeEditRequestDto.getStoreName()).spliterator(), false).findAny().isPresent()){
            throw new StoreNameIsExitsException(HttpStatus.BAD_REQUEST, "해당 이름을 가진 가게가 이미 존재합니다.");
        }

        try {

            // 값이 있어야지만 데이터 변경 가능
            if (storeEditRequestDto.getStoreName() != null && !storeEditRequestDto.getStoreName().isEmpty()) {
                store.setStoreName(storeEditRequestDto.getStoreName());
            }

            if (storeEditRequestDto.getOpenTime() != null) {
                store.setOpenTime(dateTImeService.getCurrentDateTime(storeEditRequestDto.getOpenTime()));
            }

            if (storeEditRequestDto.getCloseTime() != null) {
                store.setCloseTime(dateTImeService.getCurrentDateTime(storeEditRequestDto.getCloseTime()));
            }

            if (storeEditRequestDto.getMinOrderPrice() != null) {
                store.setMinOrderPrice(storeEditRequestDto.getMinOrderPrice());
            }

            LocalDateTime now = LocalDateTime.now();

            return StoreEditResponseDto.builder()
                    .updatedAt(now)
                    .build();
        } catch(RuntimeException e) {
            throw new RuntimeException("상점 수정도중 알수 없는 에러가 발생하였습니다.");
        }

    }

    public List<StoresSearchResponseDto> findStoresByStoreName(String storeName) {

        Iterable<Store> stores = storeRepository.findByStoreNameLike(storeName);

        List<StoresSearchResponseDto> storeResponseDtoList = new ArrayList<>();
        for(Store store : stores){
            storeResponseDtoList.add(
                    StoresSearchResponseDto.builder()
                            .storeId(store.getStoreId())
                            .storeName(store.getStoreName())
                            .openTime(store.getOpenTime())
                            .closeTime(store.getCloseTime())
                            .minOrderPrice(store.getMinOrderPrice())
                            .build()
            );
        }

        return storeResponseDtoList;

    }


    public StoreFindResponseDto findStoreByStoreId(Long storeId) {
        Store store = getStore(storeId);

        List<Menu> menuList = menuRepository.findAllActiveByStoreId(storeId);

        // 메뉴 리스트 추가
        return StoreFindResponseDto.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .minOrderPrice(store.getMinOrderPrice())
                .menuList(menuList)
                .build();

    }


    public Store getStore(Long storeId) {
        return storeRepository.findByStoreId(storeId).orElseThrow(() -> new NotFoundStoreException(HttpStatus.BAD_REQUEST, "상점을 찾을 수 없습니다."));
    }

    public void checkPermission(Long requestUserId, Long targetUserId){
        if(!requestUserId.equals(targetUserId))
            throw new PermissionDefinedStoreUpdateException(HttpStatus.UNAUTHORIZED, "해당 가게에 대한 권한이 없습니다.");
    }


    @Transactional
    public void storeClose(AuthUser authUser, Long storeId) {

        Store store = getStore(storeId);
        // 권한 체크
        checkPermission(authUser.getId(), store.getUserId());

        try {
            store.setStatusOpen(false);
            store.setStatusShutdown(true);
        } catch (RuntimeException e){
            throw new RuntimeException("폐업 작업 중 알수없는 에러가 발생하였습니다.");
        }
    }

    public List<StoreStatisticsResponseDto> storeStatistics(AuthUser authUser, StoreStatisticsRequestDto storeStatisticsRequestDto) {

        if(!authUser.getUserRole().equals(UserRole.OWNER)){
            throw new PermissionDefinedOwnerException(HttpStatus.UNAUTHORIZED, "사장님 권한을 가진 유저만 조회가 가능합니다.");
        }

        if(storeStatisticsRequestDto.getDataType().equals("month") && (!storeStatisticsRequestDto.getStartDate().matches("^\\d{4}-\\d{2}$") || !storeStatisticsRequestDto.getEndDate().matches("^\\d{4}-\\d{2}$"))){
            throw new DateFormatException(HttpStatus.BAD_REQUEST, "잘못된 날자 형태입니다. yyyy-mm");
        }

        if(storeStatisticsRequestDto.getDataType().equals("day") && (!storeStatisticsRequestDto.getStartDate().matches("^\\d{4}-\\d{2}-\\d{2}$") || !storeStatisticsRequestDto.getEndDate().matches("^\\d{4}-\\d{2}-\\d{2}$"))){
            throw new DateFormatException(HttpStatus.BAD_REQUEST, "잘못된 날자 형태입니다. yyyy-mm-dd");
        }

        List<Tuple> storeStatistics = null;

        List<StoreStatisticsResponseDto> dtoList = new ArrayList<>();

        // 월별 통계
        if(storeStatisticsRequestDto.getDataType().equals("month")){
            storeStatistics = storeRepository.findByAllStoresStatistics(storeStatisticsRequestDto.getStartDate(), storeStatisticsRequestDto.getEndDate(), authUser.getId());
        }

        // 일별 통계
        if(storeStatisticsRequestDto.getDataType().equals("day")){
            storeStatistics = storeRepository.findByStoresStatistics(storeStatisticsRequestDto.getStartDate(), storeStatisticsRequestDto.getEndDate(), authUser.getId());
        }

        for (Tuple result : storeStatistics) {
            StoreStatisticsResponseDto dto = new StoreStatisticsResponseDto(
                    result.get("order_date", String.class),
                    result.get("total_orders", Long.class),
                    result.get("total_sales", BigDecimal.class)
            );
            dtoList.add(dto);
        }

        return dtoList;

    }


}
