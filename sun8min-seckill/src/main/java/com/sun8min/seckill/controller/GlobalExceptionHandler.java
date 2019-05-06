package com.sun8min.seckill.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
    public String jsonErrorHandler(HttpServletRequest req, Exception e) {
        return "error";
    }
}
