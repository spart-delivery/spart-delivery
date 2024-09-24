package com.sparta.spartdelivery.domain.menu.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class MenuExistsException extends CommonException {
    public MenuExistsException(HttpStatus status,String message)
    {super(status,message);}
}
