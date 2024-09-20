package com.sparta.spartdelivery.menu.repository;

import com.sparta.spartdelivery.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
