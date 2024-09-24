package com.sparta.spartdelivery.domain.store.controller;

import com.sparta.spartdelivery.common.annotation.Auth;
import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
import com.sparta.spartdelivery.domain.store.dto.request.StoreEditRequestDto;
import com.sparta.spartdelivery.domain.store.dto.request.StoreSaveRequestDto;
import com.sparta.spartdelivery.domain.store.dto.request.StoreStatisticsRequestDto;
import com.sparta.spartdelivery.domain.store.dto.response.*;
import com.sparta.spartdelivery.domain.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 가게 생성
     * @param
     * @return
     */
    @PostMapping
    public ResponseEntity<CommonResponseDto<StoreSaveResponseDto>> storeEdit(@Valid @RequestBody StoreSaveRequestDto storeSaveRequestDto, @Auth AuthUser authUser){

        StoreSaveResponseDto storeSaveResponseDto = storeService.saveStore(authUser, storeSaveRequestDto);

        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", storeSaveResponseDto), HttpStatus.OK);
    }


    /**
     * 가게 수정
     * @param
     * @return
     */
    @PutMapping("/{storeId}")
    public ResponseEntity<CommonResponseDto<StoreEditResponseDto>> storeEdit(@Auth AuthUser authUser, @PathVariable("storeId") Long storeId, @Valid @RequestBody StoreEditRequestDto storeEditRequestDto){

        StoreEditResponseDto storeSearchResponseDto = storeService.updateStore(authUser, storeId, storeEditRequestDto);

        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", storeSearchResponseDto), HttpStatus.OK);
    }

    /**
     * 가게 조회 다건, 메뉴 x
     * @param
     * @return
     */
    @GetMapping
    public ResponseEntity<CommonResponseDto<List<StoresSearchResponseDto>>> searchStore(@RequestParam String storeName){

        List<StoresSearchResponseDto> storesSearchResponseDto = storeService.findStoresByStoreName(storeName);

        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", storesSearchResponseDto), HttpStatus.OK);
    }

    /**
     * 가게 조회 단건, 메뉴 x
     * @param
     * @return
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<CommonResponseDto<StoreFindResponseDto>> store(@PathVariable("storeId") Long storeId){

        StoreFindResponseDto storeFindResponseDto = storeService.findStoreByStoreId(storeId);

        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", storeFindResponseDto), HttpStatus.OK);
    }

    /**
     * 가게 폐업 처리
     * @param
     * @return
     */
    @DeleteMapping("/{storeId}")
    public ResponseEntity<CommonResponseDto<Void>> storeClose(@Auth AuthUser authUser, @PathVariable("storeId") Long storeId){

        storeService.storeClose(authUser, storeId);

        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", null), HttpStatus.OK);
    }


    /**
     * 가게 대시보드
     * 사장님은 본인의 가게에 대한 통계를 볼 수 있습니다.
     * 일간/월간 고객수 및 매출
     * @param
     * @return
     */
    @GetMapping("/statistics")
    public ResponseEntity<CommonResponseDto<List<StoreStatisticsResponseDto>>> storeStatistics(@Auth AuthUser authUser, @Valid @RequestBody StoreStatisticsRequestDto storeStatisticsRequestDto){

        List<StoreStatisticsResponseDto> storeStatisticsResponseDtoList = storeService.storeStatistics(authUser, storeStatisticsRequestDto);

        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", storeStatisticsResponseDtoList), HttpStatus.OK);
    }

}
