package com.sparta.spartdelivery.domain.store.service;

import com.sparta.spartdelivery.common.service.DateTImeService;
import com.sparta.spartdelivery.domain.menu.repository.MenuRepository;
import com.sparta.spartdelivery.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    DateTImeService dateTImeService;

    @InjectMocks
    private StoreService storeService;


    @Test
    void saveTest(){
        System.out.println("test");
    }

}