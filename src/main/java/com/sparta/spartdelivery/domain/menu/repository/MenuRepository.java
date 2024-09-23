package com.sparta.spartdelivery.domain.menu.repository;

import com.sparta.spartdelivery.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    /* 메뉴 조회할 때 ACTIVE 상태 메뉴만 반환*/
    @Query("SELECT m FROM Menu m WHERE m.storeId = :storeId AND m.status = 'ACTIVE'")
    List<Menu> findAllActiveByStoreId(@Param("storeId") Long storeId);

    /* 가게의 같은 메뉴를 찾는 메서드 추가 */
    Optional<Menu> findByStoreIdAndMenuName(Long storeId, String menuName);
}
