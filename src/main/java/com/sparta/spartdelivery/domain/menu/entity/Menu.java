package com.sparta.spartdelivery.domain.menu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="menu_id")
    private Long menuId;

    @Column(nullable = false, name="store_id")
    private Long storeId;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private int menuPrice;

    // 상태 추가
    @Enumerated(EnumType.STRING)
    private MenuStatus status = MenuStatus.ACTIVE; /* 기본값은 ACTIVE */

    public Menu( String menuName, int menuPrice, Long storeId){
        this.menuName=menuName;
        this.menuPrice=menuPrice;
        this.storeId=storeId;
    }
    public void update(String menuName, int menuPrice){
        this.menuName=menuName;
        this.menuPrice=menuPrice;
    }
    public void withdraw() {
        this.status = MenuStatus.WITHDRAWN;  /* 상태를 WITHDRAWN으로 변경 */
    }
}
