package com.sparta.spartdelivery.domain.menuCategory.repository;

import com.sparta.spartdelivery.domain.menuCategory.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

}
