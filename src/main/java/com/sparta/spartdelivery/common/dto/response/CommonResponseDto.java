package com.sparta.spartdelivery.common.dto.response;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommonResponseDto<T> {

    private int statusCode;

    private String message;

    private T data;

    public CommonResponseDto(HttpStatus status, String message, T data) {
        this.statusCode = status.value();
        this.message = message;
        this.data = data;
    }
}
