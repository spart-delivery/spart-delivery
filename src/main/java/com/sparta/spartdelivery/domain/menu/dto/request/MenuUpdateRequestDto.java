    package com.sparta.spartdelivery.domain.menu.dto.request;

    import lombok.Getter;

    @Getter
    public class MenuUpdateRequestDto {
        private String menuName;
        private int menuPrice;

        public MenuUpdateRequestDto(String menuName, int menuPrice) {
            this.menuName = menuName;
            this.menuPrice = menuPrice;
        }
    }
