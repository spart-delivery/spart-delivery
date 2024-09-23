package com.sparta.spartdelivery.domain.store.repository;

import com.sparta.spartdelivery.domain.store.entity.Store;
import jakarta.persistence.Tuple;
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


    // 사장님 가게의 매출 통계
    @Query(value = "WITH RECURSIVE date_range AS ( " +
            "    SELECT :startDate AS order_date " +
            "    UNION ALL " +
            "    SELECT DATE_ADD(order_date, INTERVAL 1 DAY) " +
            "    FROM date_range " +
            "    WHERE order_date < :endDate " +
            ") " +
            "SELECT " +
            "    d.order_date, " +
            "    o.store_id, " +
            "    COALESCE(SUM(o.menu_price), 0) AS total_sales, " +
            "    COALESCE(COUNT(o.order_id), 0) AS total_orders " +
            "FROM " +
            "    date_range d " +
            "LEFT JOIN " +
            "    ( " +
            "        SELECT " +
            "            DATE_FORMAT(ods.created_at, '%Y-%m-%d') AS order_date, " +
            "            mn.menu_price, " +
            "            st.store_id, " +
            "            ods.order_id " +
            "        FROM orders ods " +
            "        LEFT JOIN store st ON st.store_id = ods.store_id " +
            "        LEFT JOIN menu mn ON mn.menu_id = ods.menu_id " +
            "        WHERE st.user_id = :userId " +
            "        AND ods.status = 'COMPLETED' " +
            "    ) o ON d.order_date = o.order_date " +
            "GROUP BY " +
            "    d.order_date, o.store_id " +
            "ORDER BY " +
            "    d.order_date, o.store_id", nativeQuery = true)
    List<Tuple> findByStoresStatistics(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Long userId);



    // 배달어플의 매출 통계
    @Query(value = "WITH RECURSIVE date_range AS ( " +
            "    SELECT CONCAT(:startDate, '-01') AS order_date " +
            "    UNION ALL " +
            "    SELECT DATE_ADD(order_date, INTERVAL 1 MONTH) " +
            "    FROM date_range " +
            "    WHERE order_date < CONCAT(:endDate, '-01') " +
            ") " +
            "SELECT " +
            "    DATE_FORMAT(d.order_date, '%Y-%m') AS order_date, " +
            "    o.store_id, " +
            "    COALESCE(SUM(o.menu_price), 0) AS total_sales, " +
            "    COALESCE(COUNT(o.order_id), 0) AS total_orders " +
            "FROM " +
            "    date_range d " +
            "LEFT JOIN " +
            "    ( " +
            "        SELECT " +
            "            DATE_FORMAT(ods.created_at, '%Y-%m') AS order_date, " +
            "            mn.menu_price, " +
            "            st.store_id, " +
            "            ods.order_id " +
            "        FROM orders ods " +
            "        LEFT JOIN store st ON st.store_id = ods.store_id " +
            "        LEFT JOIN menu mn ON mn.menu_id = ods.menu_id " +
            "        WHERE st.user_id = :userId " +
            "        AND ods.status = 'COMPLETED' " +
            "    ) o ON DATE_FORMAT(d.order_date, '%Y-%m') = o.order_date " +
            "GROUP BY " +
            "    d.order_date, o.store_id " +
            "ORDER BY " +
            "    d.order_date, o.store_id", nativeQuery = true)
    List<Tuple> findByAllStoresStatistics(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Long userId);

}

