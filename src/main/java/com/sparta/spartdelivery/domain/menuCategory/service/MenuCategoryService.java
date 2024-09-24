package com.sparta.spartdelivery.domain.menuCategory.service;

import com.sparta.spartdelivery.domain.menu.entity.Menu;
import com.sparta.spartdelivery.domain.menu.repository.MenuRepository;
import com.sparta.spartdelivery.domain.menuCategory.dto.request.MenuCategoryDetailRequestDto;
import com.sparta.spartdelivery.domain.menuCategory.dto.request.MenuCategorySaveRequestDto;
import com.sparta.spartdelivery.domain.menuCategory.dto.response.MenuCategoryDetailResponseDto;
import com.sparta.spartdelivery.domain.menuCategory.dto.response.MenuCategorySaveResponseDto;
import com.sparta.spartdelivery.domain.menuCategory.dto.response.MenuCategorySimpleResponseDto;
import com.sparta.spartdelivery.domain.menuCategory.entity.MenuCategory;
import com.sparta.spartdelivery.domain.menuCategory.repository.MenuCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuCategoryService {

    private final MenuRepository menuRepository;
    private MenuCategoryRepository menuCategoryRepository;

    /* 카테고리 생성  */
    @Transactional
    public MenuCategorySaveResponseDto saveCategory(MenuCategorySaveRequestDto menuCategorySaveRequestDto) {
        MenuCategory newCategory = new MenuCategory(menuCategorySaveRequestDto.getCategoryName());
        MenuCategory savedCategory = menuCategoryRepository.save(newCategory);

        return new MenuCategorySaveResponseDto(
                savedCategory.getCategoryName());
    }

    /* 카테고리 전체조회 */
    public List<MenuCategorySimpleResponseDto> getSimpleCategory() {
        List<MenuCategory> menuCategoryList = menuCategoryRepository.findAll();
        List<MenuCategorySimpleResponseDto> dtoList = new ArrayList<>();
        for (MenuCategory menuCategory : menuCategoryList) {
            dtoList.add(new MenuCategorySimpleResponseDto(menuCategory.getCategoryName()));
        }
        return dtoList;
    }

    /* 카테고리 단건조회 ( 각 해당하는 카테고리의 메뉴 나오기 ) */
    public MenuCategoryDetailResponseDto getDetailCategory(Long categoryId) {
        MenuCategory menuCategory = menuCategoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new NullPointerException("찾는 categoryId가 없습니다."));

        /* 해당 카테고리와 연관된 메뉴 조회 */
        List<Menu> menus = menuRepository.findByCategoryId(categoryId);
        List<String> menuNames = new ArrayList<>();

        for (Menu menu : menus) {
            menuNames.add(menu.getMenuName());
        }
        return new MenuCategoryDetailResponseDto(menuNames);
    }

    /* 카테고리 삭제 */
    @Transactional
    public void deleteCategory(Long categoryId) {
        MenuCategory menuCategory = menuCategoryRepository.findById(categoryId)
                .orElseThrow(
                        ()-> new NullPointerException("찾는 categoryId가 없습니다."));
        menuCategoryRepository.delete(menuCategory);
    }
}
