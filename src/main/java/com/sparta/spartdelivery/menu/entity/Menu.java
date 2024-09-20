package com.sparta.spartdelivery.menu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long storeId;
    private String menuName;
    private int menuPrice;
    private boolean deleted = false;

    public Menu(Long storeId,String menuName, int menuPrice){
        this.storeId=storeId;
        this.menuName=menuName;
        this.menuPrice=menuPrice;
    }
    public void update(String menuName, int menuPrice){
        this.menuName=menuName;
        this.menuPrice=menuPrice;
    }
    public void delete() {
        this.deleted = true;
    }
}
