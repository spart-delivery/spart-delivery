package com.sparta.spartdelivery.menu.repository;

import com.sparta.spartdelivery.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    /* 메뉴 조회할 때 ACTIVE 상태 메뉴만 반환*/
    @Query("SELECT m FROM Menu m WHERE m.storeId = :storeId AND m.status = 'ACTIVE'")
    List<Menu> findAllActiveByStoreId(@Param("storeId") Long storeId);
}
