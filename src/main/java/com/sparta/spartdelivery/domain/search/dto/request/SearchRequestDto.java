package com.sparta.spartdelivery.domain.search.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SearchRequestDto {
    @NotBlank(message = "검색어는 필수입니다.")
    private String keyword;
}
