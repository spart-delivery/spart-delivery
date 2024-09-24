package com.sparta.spartdelivery.domain.menu.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class MenuIdNotFoundException extends CommonException {
    public MenuIdNotFoundException(HttpStatus status, String message)
    {super(status,message);}
}
