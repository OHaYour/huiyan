package com.huiyan.config;

import com.huiyan.pojo.User;
import com.huiyan.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权的方法");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //增加AAA的权限
//        info.addStringPermission("AAA");

        //拿到当前登录的对象
        //从认证中的principal取到user对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        info.addStringPermission(currentUser.getUserPerms());

        String value=redisTemplate.opsForValue().get(currentUser.getUserId());
        if (value==null){
            value="unVip";
        }
        info.addStringPermission(value);

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证的方法");

        //用户名，密码  数据库中读
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        User user1=new User();
        user1.setUserEmail(userToken.getUsername());

        User user = userService.queryUserInfo(user1);

//        if(!userToken.getUsername().equals(user.getName())){
//            //用户名不存在，抛出异常 UnknownAccountException
//            return null;
//        }

        if (user==null){
            //没有该用户  UnknownAccountException
            return null;
        }

        Subject currentSubject = SecurityUtils.getSubject();
        Session session=currentSubject.getSession();
        session.setAttribute("loginUser",user);

        //参数一：(principal)将user信息存入subject对象，让doGetAuthorizationInfo进行授权操作
        //参数二：shiro进行密码验证
        return new SimpleAuthenticationInfo(user,user.getUserPassword(),"");
    }
}
