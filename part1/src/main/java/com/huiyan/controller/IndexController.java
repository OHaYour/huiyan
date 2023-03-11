package com.huiyan.controller;

import com.huiyan.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;


@Controller
public class IndexController {


    /**
     * 控制去首页
     * @return
     */
    @RequestMapping({"/","/index"})
    public String toIndex(HttpSession session){
        return "index";
    }

    @RequestMapping("/user")
    public String toUser(){
        return "userIndex";
    }

    @RequestMapping("/toUserShiMing")
    public String toUserShiMing(){
        return "userShiMing";
    }

}
