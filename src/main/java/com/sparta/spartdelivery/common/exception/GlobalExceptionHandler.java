package com.sparta.spartdelivery.common.exception;

import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    // start 특정 exception 에 대한 글로벌 처리
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonResponseDto<Object>> handleNotFoundDataException(CommonException ex) {
        return getErrorResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonResponseDto<Object>> handleInvalidDateFormat(HttpMessageNotReadableException ex) {
        // 오류가 발생한 원인이 LocalDateTime 변환 문제인지를 확인
        if (ex.getMessage().contains("java.time.LocalDateTime")) {
            return getErrorResponse(HttpStatus.BAD_REQUEST, "날짜 형식이 잘못되었습니다. 올바른 형식은 yyyy-MM-dd HH:mm:ss 입니다.");
        }

        // 다른 경우는 일반적인 메시지 반환
        return getErrorResponse(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
    }

    // end 특정 exception 에 대한 글로벌 처리

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponseDto<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String lastDefaultMessage = fieldErrors.get(fieldErrors.size() - 1).getDefaultMessage();

        return getErrorResponse(HttpStatus.BAD_REQUEST, lastDefaultMessage);
    }

    public ResponseEntity<CommonResponseDto<Object>> getErrorResponse(HttpStatus status, String message) {

        log.info(message);

        return new ResponseEntity<>(new CommonResponseDto<>(status.value(), message, null), status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponseDto<Object>> handleRuntimeException(RuntimeException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return getErrorResponse(status, ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponseDto<Object>> AccessDeniedException(AccessDeniedException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return getErrorResponse(status, ex.getMessage());
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<CommonResponseDto<Object>> handleIllegalAccessException(IllegalAccessException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return getErrorResponse(status, ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<CommonResponseDto<Object>> handleNullPointerException(NullPointerException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 적절한 HTTP 상태 코드 선택
        return getErrorResponse(status, "Null pointer exception occurred: " + ex.getMessage());
    }
}