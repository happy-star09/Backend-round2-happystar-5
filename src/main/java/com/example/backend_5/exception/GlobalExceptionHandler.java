package com.example.backend_5.exception;

import com.example.backend_5.pojo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 全局异常注解
public class GlobalExceptionHandler {

    // 拦截空值校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 强制返回HTTP 400
    public Result handleValidationException(MethodArgumentNotValidException e) {
        FieldError fl = e.getBindingResult().getFieldError();
        String errorMsg = fl != null ? fl.getDefaultMessage() : "参数校验失败";
        return Result.error(errorMsg);
    }

    // 拦截业务逻辑异常（在Service里面throw的RuntimeException）
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleRuntimeException(RuntimeException e) {
        return Result.error(e.getMessage());
    }
}
