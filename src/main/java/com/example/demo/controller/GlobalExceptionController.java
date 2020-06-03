package com.example.demo.controller;

import com.example.demo.utils.JsonReponseBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author zhanghaoyang
 */
@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(value = {Exception.class})
    public String exceptionHandle(ServletResponse response, Exception e) {
        response.setCharacterEncoding("UTF-8");
        return new JsonReponseBuilder().addItems("state", "200", "msg", "error").build();
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public String runtimeExceptionHandle(ServletResponse response, Exception e) throws IOException, ServletException, InterruptedException {
        response.setCharacterEncoding("UTF-8");
        return new JsonReponseBuilder().addItems("state", "500", "msg", e.getMessage()).build();
    }
}
