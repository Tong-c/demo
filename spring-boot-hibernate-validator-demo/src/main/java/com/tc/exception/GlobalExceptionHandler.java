package com.tc.exception;

import com.tc.common.R;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        StringBuilder sb = new StringBuilder("");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = removeEnChar(sb);
        return new R(4000, msg);
    }

    private String removeEnChar(StringBuilder sb) {
        if (sb.toString().contains("：")) {
            return sb.toString().split("：")[1];
        }
        return sb.toString();
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public R handleException(ConstraintViolationException exception) {
        String msg = removeEnChar(new StringBuilder(exception.getMessage()));
        return new R(4000, msg);
    }

}
