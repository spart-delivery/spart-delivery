package com.sparta.spartdelivery.domain.menuCategory.controller;

import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
import com.sparta.spartdelivery.domain.menuCategory.dto.request.MenuCategorySaveRequestDto;
import com.sparta.spartdelivery.domain.menuCategory.dto.response.MenuCategoryDetailResponseDto;
import com.sparta.spartdelivery.domain.menuCategory.dto.response.MenuCategorySaveResponseDto;
import com.sparta.spartdelivery.domain.menuCategory.dto.response.MenuCategorySimpleResponseDto;
import com.sparta.spartdelivery.domain.menuCategory.service.MenuCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuCategoryController {
    private final MenuCategoryService menuCategoryService;

    /* 카테고리 생성 */
    @PostMapping("/category")
    public ResponseEntity<CommonResponseDto<MenuCategorySaveResponseDto>> saveCategory(
            @RequestBody MenuCategorySaveRequestDto menuCategorySaveRequestDto) {
        MenuCategorySaveResponseDto savedCategory = menuCategoryService.saveCategory(menuCategorySaveRequestDto);
        return ResponseEntity.ok(new CommonResponseDto<>(HttpStatus.OK, "success", savedCategory));
    }

    /* 카테고리 전체 조회 */
    @GetMapping("/category")
    public ResponseEntity<CommonResponseDto<List<MenuCategorySimpleResponseDto>>> getSimpleCategory() {
        List<MenuCategorySimpleResponseDto> categories = menuCategoryService.getSimpleCategory();
        return ResponseEntity.ok(new CommonResponseDto<>(HttpStatus.OK, "success", categories));
    }

    /* 카테고리 단건 조회 */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CommonResponseDto<MenuCategoryDetailResponseDto>> getDetailCategory(@PathVariable Long categoryId) {
        MenuCategoryDetailResponseDto detailResponseDto = menuCategoryService.getDetailCategory(categoryId);
        return ResponseEntity.ok(new CommonResponseDto<>(HttpStatus.OK, "success", detailResponseDto));
    }

    /* 카테고리 삭제 */
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteCategory(@PathVariable Long categoryId) {
        menuCategoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(new CommonResponseDto<>(HttpStatus.OK, "success", null));
    }
}
