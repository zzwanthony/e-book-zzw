package com.zzw.common;

import com.zzw.Exception.WebException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyExceptionHandler {
    public static final String ERROR_VIEW="error";
    //@ExceptionHandler (value = Exception.class) 表示对Exception异常进行捕获
    @ExceptionHandler(value = WebException.class)
    public Object errorHandler(HttpServletRequest request, HttpServletResponse response, WebException e) throws Exception{
        ModelAndView mav=new ModelAndView();
        mav.addObject("exception",e.getMessage());
        mav.addObject("url",request.getRequestURI());
        mav.setViewName(ERROR_VIEW);
        return mav;
    }
}
