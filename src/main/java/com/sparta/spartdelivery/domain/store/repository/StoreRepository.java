package com.sparta.spartdelivery.domain.store.repository;

import com.sparta.spartdelivery.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query(value = "SELECT * FROM store WHERE user_id = :userId AND status_shutdown != 1 order by store_id desc", nativeQuery = true)
    List<Store> findByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM store WHERE store_id = :storeId AND status_shutdown != 1 order by store_id desc", nativeQuery = true)
    Optional<Store> findByStoreId(@Param("storeId") Long storeId);

    @Query(value = "SELECT * FROM store WHERE store_name = :storeName order by store_id desc", nativeQuery = true)
    Iterable<Store> findByStoreName(@Param("storeName") String storeName);

    @Query(value = "SELECT * FROM store WHERE store_name LIKE %:storeName% AND status_shutdown != 1 order by store_id desc", nativeQuery = true)
    List<Store> findByStoreNameLike(@Param("storeName") String storeName);
}

