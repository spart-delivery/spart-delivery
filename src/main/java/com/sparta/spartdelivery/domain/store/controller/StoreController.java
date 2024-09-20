package com.sparta.spartdelivery.domain.store.controller;

import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
import com.sparta.spartdelivery.domain.store.dto.request.StoreEditRequestDto;
import com.sparta.spartdelivery.domain.store.dto.request.StoreSaveRequestDto;
import com.sparta.spartdelivery.domain.store.dto.response.StoreEditResponseDto;
import com.sparta.spartdelivery.domain.store.dto.response.StoreFindResponseDto;
import com.sparta.spartdelivery.domain.store.dto.response.StoreSaveResponseDto;
import com.sparta.spartdelivery.domain.store.dto.response.StoresSearchResponseDto;
import com.sparta.spartdelivery.domain.store.service.StoreService;
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
    public ResponseEntity<CommonResponseDto<StoreSaveResponseDto>> storeEdit(@RequestBody StoreSaveRequestDto storeSaveRequestDto){

        StoreSaveResponseDto storeSaveResponseDto = storeService.saveStore(storeSaveRequestDto);

        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", storeSaveResponseDto), HttpStatus.OK);
    }


    /**
     * 가게 수정
     * @param
     * @return
     */
    @PatchMapping
    public ResponseEntity<CommonResponseDto<StoreEditResponseDto>> storeEdit(@RequestBody StoreEditRequestDto storeEditRequestDto){

        StoreEditResponseDto storeSaveResponseDto = storeService.findStores(storeEditRequestDto);

        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", storeSaveResponseDto), HttpStatus.OK);
    }

    /**
     * 가게 조회 다건, 메뉴 x
     * @param
     * @return
     */
    @GetMapping
    public ResponseEntity<CommonResponseDto<List<StoresSearchResponseDto>>> searchStore(@RequestParam String storeName){

        List<StoresSearchResponseDto> storeSaveResponseDto = storeService.findStoresByStoreName(storeName);

        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", storeSaveResponseDto), HttpStatus.OK);
    }

    /**
     * 가게 조회 단건, 메뉴 x
     * @param
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponseDto<StoreFindResponseDto>> store(@PathVariable Long id){

        StoreFindResponseDto storeSaveResponseDto = storeService.findStoreByStoreId(id);

        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", storeSaveResponseDto), HttpStatus.OK);
    }

}
