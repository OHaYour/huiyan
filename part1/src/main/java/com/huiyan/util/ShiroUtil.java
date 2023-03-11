package com.huiyan.util;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ShiroUtil {

        @ResponseBody
        @ExceptionHandler(UnauthorizedException.class)
        public String handleShiroException(Exception ex) {
            return  ex.getMessage();
        }
        @ResponseBody
        @ExceptionHandler(AuthorizationException.class)
        public String AuthorizationException(Exception ex) {
            return ex.getMessage();
        }

}
