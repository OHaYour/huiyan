package com.huiyan.convert;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理时间类型转换
 */
public class MyDateConvert implements Converter<String, Date> {
    @SneakyThrows
    @Override
    public Date convert(String s) {
        try{
            //字符串格式的时间类型转为Date类型的时间
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date date=sdf.parse(s);
            System.out.println("调用了Date转化");
            return date;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
