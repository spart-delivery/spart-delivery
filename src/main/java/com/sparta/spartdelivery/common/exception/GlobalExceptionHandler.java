package com.sparta.spartdelivery.common.exception;

import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}