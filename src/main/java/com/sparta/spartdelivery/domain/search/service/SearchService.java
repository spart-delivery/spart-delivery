package com.sparta.spartdelivery.domain.search.service;

import com.sparta.spartdelivery.domain.menu.entity.Menu;
import com.sparta.spartdelivery.domain.menu.repository.MenuRepository;
import com.sparta.spartdelivery.domain.search.dto.response.MenuResponseDto;
import com.sparta.spartdelivery.domain.search.dto.response.SearchResponseDto;
import com.sparta.spartdelivery.domain.search.dto.response.StoreResponseDto;
import com.sparta.spartdelivery.domain.store.entity.Store;
import com.sparta.spartdelivery.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    // keyword(검색어)를 받아서 가게나 메뉴 중 이 단어가 들어간 항목들을 찾는다.
    public SearchResponseDto search(String keyword) {
        List<Store> stores = storeRepository.findBystoreNameContaining(keyword);
        List<Menu> menus = menuRepository.findBymenuNameContaining(keyword);

        List<StoreResponseDto> storeResponseDtos = stores.stream()
                .map(store -> new StoreResponseDto(
                        store.getStoreId(),
                        store.getStoreName(),
                        store.getOpenTime(),
                        store.getCloseTime(),
                        store.getMinOrderPrice()
                ))
                .collect(Collectors.toList());

        List<MenuResponseDto> menuResponseDtos = menus.stream()
                .map(menu -> new MenuResponseDto(
                        menu.getMenuId(),
                        menu.getMenuName(),
                        menu.getMenuPrice()
                ))
                .collect(Collectors.toList());

        return new SearchResponseDto(storeResponseDtos, menuResponseDtos);
    }
}