package com.sparta.spartdelivery.domain.store.repository;

import com.sparta.spartdelivery.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByStoreId(Long storeId);

    Iterable<Store> findByStoreName(String storeName);

}

