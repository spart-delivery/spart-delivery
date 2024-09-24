//package com.sparta.spartdelivery.domain.search.controller;
//
//import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
//import com.sparta.spartdelivery.domain.search.dto.request.SearchRequestDto;
//import com.sparta.spartdelivery.domain.search.dto.response.SearchResponseDto;
//import com.sparta.spartdelivery.domain.search.service.SearchService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//@RestController
//@RequestMapping("/api/search")
//@RequiredArgsConstructor
//public class SearchController {
//    private final SearchService searchService;
//
//    @PostMapping // POST 요청을 처리하도록 설정
//    public ResponseEntity<CommonResponseDto<SearchResponseDto>> search(
//            @Valid @RequestBody SearchRequestDto searchRequestDto) {
//
//        SearchResponseDto searchResponseDto = searchService.search(String.valueOf(searchRequestDto));
//
//        // CommonResponseDto 생성
//        CommonResponseDto<SearchResponseDto> responseDto = new CommonResponseDto<>(
//                HttpStatus.OK,
//                "검색이 성공적으로 완료되었습니다.",
//                searchResponseDto // 검색 결과 DTO
//        );
//
//        return ResponseEntity.ok(responseDto);
//    }
//}
