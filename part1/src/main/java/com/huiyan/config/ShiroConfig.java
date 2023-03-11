package com.huiyan.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //第三步：ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        /*
        anon：无需认证就可以访问
        authc：必须认证了才能访问
        user：必须拥有“记住我”功能才能访问
        perms：拥有对某个资源的权限才能访问
        role：拥有某个角色权限才能访问
         */

        //拦截
        Map<String, String> filterMap=new LinkedHashMap<>();
//        filterMap.put("/user/add","anon");
//        filterMap.put("/user/update","authc");

        //授权,未授权会跳转页面,要写在authc前面，否则不生效
//        filterMap.put("/user/add","perms[AAA]");
//        filterMap.put("/user/update","perms[UUU]");
//        filterMap.put("/user/userLogin","anon");
//        filterMap.put("/user/toLogin","anon");
//        filterMap.put("/user/toRegister1","anon");
//        filterMap.put("/user/toRegister2","anon");
//        filterMap.put("/user/toRegister3","anon");
//        filterMap.put("/user/checkEmail","anon");
//        filterMap.put("/user/toTermsCondition","anon");
//        filterMap.put("/user/sendVCode","anon");
//        filterMap.put("/user/RegInfo","anon");

        filterMap.put("/reply/addTopicReply","perms[shiming]");
        filterMap.put("/topic/addTopic","perms[shiming]");

        //放开shirovip验证
//        filterMap.put("/user/toVipCube","perms[vip]");
//        filterMap.put("/user/toVipLottery","perms[vip]");


        filterMap.put("/video/setToVideo","authc");
        filterMap.put("/user/*","anon");

        bean.setFilterChainDefinitionMap(filterMap);

        //设置登录的请求
        bean.setLoginUrl("/user/toLogin");
        //设置未授权的页面
        bean.setUnauthorizedUrl("/user/toUserVip");
        return bean;
    }

    //第二步：DafultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //第一步：创建realm对象，需要自定义类
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合ShiroDialect：用来整合Shiro-Thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
