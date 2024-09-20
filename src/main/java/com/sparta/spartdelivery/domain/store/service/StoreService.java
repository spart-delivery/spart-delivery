package com.sparta.spartdelivery.domain.store.service;

import com.sparta.spartdelivery.domain.store.dto.request.StoreEditRequestDto;
import com.sparta.spartdelivery.domain.store.dto.request.StoreSaveRequestDto;
import com.sparta.spartdelivery.domain.store.dto.response.StoreEditResponseDto;
import com.sparta.spartdelivery.domain.store.dto.response.StoreFindResponseDto;
import com.sparta.spartdelivery.domain.store.dto.response.StoreSaveResponseDto;
import com.sparta.spartdelivery.domain.store.dto.response.StoresSearchResponseDto;
import com.sparta.spartdelivery.domain.store.entity.Store;
import com.sparta.spartdelivery.domain.store.exception.NotFoundStoreException;
import com.sparta.spartdelivery.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreSaveResponseDto saveStore(StoreSaveRequestDto storeSaveRequestDto) {

        Store newStore = Store.builder()
                .storeName(storeSaveRequestDto.getStoreName())
                .openTime(storeSaveRequestDto.getOpenTime())
                .closeTime(storeSaveRequestDto.getCloseTime())
                .minOrderPrice(storeSaveRequestDto.getMinOrderPrice())
                .userId(storeSaveRequestDto.getUserId())
                .build();

        Store saveStore = storeRepository.save(newStore);

        return StoreSaveResponseDto.builder()
                .storeId(saveStore.getStoreId())
                .createdAt(saveStore.getCreatedAt())
                .build();

    }

    @Transactional
    public StoreEditResponseDto findStores(StoreEditRequestDto storeEditRequestDto) {

        Store store = getStore(storeEditRequestDto.getStoreId());

        store.setStoreName(storeEditRequestDto.getStoreName());
        store.setOpenTime(storeEditRequestDto.getOpenTime());
        store.setCloseTime(storeEditRequestDto.getCloseTime());
        store.setMinOrderPrice(storeEditRequestDto.getMinOrderPrice());

        LocalDateTime now = LocalDateTime.now();

        return StoreEditResponseDto.builder()
                .updatedAt(now)
                .build();

    }

    public List<StoresSearchResponseDto> findStoresByStoreName(String storeName) {

        Iterable<Store> stores = storeRepository.findByStoreName(storeName);

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


    public StoreFindResponseDto findStoreByStoreId(Long id) {
        Store store = getStore(id);

        // 메뉴 리스트 추가
        return StoreFindResponseDto.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .minOrderPrice(store.getMinOrderPrice())
                .menuList(null)
                .build();

    }


    public Store getStore(Long id) {
        return storeRepository.findByStoreId(id).orElseThrow(() -> new NotFoundStoreException(HttpStatus.BAD_REQUEST, "상점을 찾을 수 없습니다."));
    }



}
