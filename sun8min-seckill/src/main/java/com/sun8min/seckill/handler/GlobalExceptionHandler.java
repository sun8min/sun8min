package com.sun8min.seckill.handler;

import com.sun8min.base.exception.MyException;
import com.sun8min.web.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局统一异常处理
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 默认异常界面
     */
    private static final String DEFAULT_ERROR_VIEW = "error";

    @Autowired
    HttpServletRequest httpServletRequest;

    @ExceptionHandler(value = MyException.class)
    public Object myExceptionHandler(MyException e) {
        // ajax请求
        if (HttpUtils.isAjax(httpServletRequest)){
            Map<String, String> map = new HashMap<>();
            map.put("msg",e.getMessage());
            return map;
        }
        // 其他请求
        else {
            ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
            mav.addObject("msg", e.getMessage());
            return mav;
        }
    }

    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler() {
        // ajax请求
        if (HttpUtils.isAjax(httpServletRequest)){
            Map<String, String> map = new HashMap<>();
            map.put("msg", "500");
            return map;
        }
        // 其他请求
        else {
            ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
            mav.addObject("msg", "500");
            return mav;
        }
    }

}
