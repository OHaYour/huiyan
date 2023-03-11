package com.huiyan.config;


import com.huiyan.convert.MyDateConvert;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import com.interceptor.AuthInterceptor;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {



    //将自定义的时间类型转换器注册到springboot的网站开发中
    @Override
    public void addFormatters(FormatterRegistry registrry){
        registrry.addConverter(new MyDateConvert());
    }





}

