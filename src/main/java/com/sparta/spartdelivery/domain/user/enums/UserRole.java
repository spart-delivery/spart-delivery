package com.sparta.spartdelivery.domain.user.enums;


import com.sparta.spartdelivery.domain.user.exception.InvalidRequestException;

import java.util.Arrays;
//두 가지 종류의 역할(OWNER, USER)을 정의
public enum UserRole {
    //OWNER는 가게나 사업의 주인을 의미하고, USER는 일반 사용자를 의미
    OWNER, USER;
    //문자열로 입력한 역할이 OWNER나 USER 중 어떤 것인지 확인
    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                //만약 OWNER나 USER 중 하나가 아니라면, InvalidRequestException이라는 오류를 발생
                .orElseThrow(() -> new InvalidRequestException("유효하지 않은 UerRole"));
    }
}