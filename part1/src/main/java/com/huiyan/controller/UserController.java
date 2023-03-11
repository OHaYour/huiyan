package com.huiyan.controller;

import com.huiyan.pojo.Orders;
import com.huiyan.pojo.User;
import com.huiyan.service.OrdersService;
import com.huiyan.service.UserService;
import com.huiyan.service.VCodeService;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.sun.xml.internal.bind.v2.TODO;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/user")
public class UserController {

    // TODO: 2022/3/7   换用dubbo项目后改成@Reference
    @Autowired
    private UserService userService;

    @Autowired
    private VCodeService vCodeService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    /**
     * 去登录页面
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 去注册界面的第一部分，进行邮箱验证
     * @return
     */
    @RequestMapping("/toRegister1")
    public String toRegister1(){
        return "register1";
    }

    /**
     * 服务条款
     * @return
     */
    @RequestMapping("/toTermsCondition")
    public String toTermsCondition(){
        return "terms-condition";
    }

    /**
     * 进行邮箱验证：获取到用户邮箱，发送验证码
     * 将生成的验证码存入redis数据库中，添加有效时间
     * @param email
     * @return
     */
    @RequestMapping("/sendVCode")
    @ResponseBody
    public String sendVCode(String email,HttpSession session){

        //传入的email若为空，则去登录中存入的session取
        if(email.equals("")||email==""){
            User user= (User) session.getAttribute("loginUser");
            email=user.getUserEmail();
        }
        String VCode = "";
        Random random=new Random();
        for (int i = 0; i <6; i++) {
            VCode+=random.nextInt(10);
        }
        System.out.println("创建的验证码为："+VCode);
        System.out.println("创建的邮箱为："+email);

        //将生成的验证码存入redis数据库中，添加有效时间
        vCodeService.addInfoToRedis(email,VCode);



        //一个简单的邮件
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setSubject("慧眼网站注册验证码：");
        mailMessage.setText("验证码："+VCode+"此验证码只用于注册校验！五分钟之内有效！");
        mailMessage.setTo(email);
        mailMessage.setFrom("1134785440@qq.com");
        mailSender.send(mailMessage);

        return "sendEmail_Ok";

    }

    /**
     * 去登录界面的第二部分，填写详细信息
     * @return
     */
    @RequestMapping("/toRegister2")
    public String toRegister2(){
        return "register2";
    }

    /**
     * 去登录界面的第三部分，进行实名验证
     * @return
     */
    @RequestMapping("/toRegister3")
    public String toRegister3(){
        return "register3";
    }

    /**
     * 通过前端ajax传入的邮箱查询是否可用
     * @param email
     * @return
     */
    @PostMapping("/checkEmail")
    @ResponseBody
    public String checkEmail(String email){
        System.out.println("前端接收到的邮箱："+email);

        User user=new User();
        user.setUserEmail(email);

        User queryUser=userService.queryUserInfo(user);
        System.out.println("数据库中查出来的user为："+queryUser);

        if (queryUser==null||"".equals(queryUser)){
            return "1";
        }else{
            return "0";
        }
    }

    /**
     * 通过前端传入的user邮箱和验证码信息，与存入redis的验证码进行比对
     * 通过则将其存入数据库
     * @return
     */
    @RequestMapping("/RegInfo")
    @ResponseBody
    public String RegInfo(String email,String VCode){

        String userVCode=vCodeService.getValueByKey(email);

        if(VCode.equals(userVCode)){
            User user=new User();
            user.setUserEmail(email);
            userService.addUser(user);
            return "regOk";
        }else {
            return "flase";
        }


    }

    /**
     * 注册第二部分
     * 添加用户详细信息
     * @param email
     * @param nickName
     * @param password
     * @param phone
     * @param address
     * @return
     */
    @RequestMapping("/updateInfo")
    @ResponseBody
    public String updateInfo(String email,String nickName,String password,String userSex,String phone,String address){

        String imgUrl=null;
        BigDecimal bigDecimal = new BigDecimal(0);
        Random random=new Random();
        if (userSex.equals("男")){
            //从0-9中生成随机数，存储默认头像
            int i=random.nextInt(11);
            imgUrl="/headImg/default0"+i+".png";
        }else if(userSex.equals("女")){
            //从11-17中生成随机数，存储默认头像
            int j=random.nextInt(8)+11;
            imgUrl="/headImg/default"+j+".png";
        }
        User user =new User();
        user.setUserNickName(nickName);
        user.setUserPassword(password);
        user.setUserSex(userSex);
        user.setUserPhone(phone);
        user.setUserAddress(address);
        user.setUserImg(imgUrl);
        user.setUserBalance(bigDecimal);
        user.setUserLevel("0");
        user.setUserImgUrl("http://localhost:8081"+imgUrl);
        user.setUserRealName("未实名");
        user.setUserPerms("zero");
        user.setUserVipDay("0");

        userService.updateUser(user,email);

        return "updateOk";
    }

    /**
     * 注册第三部分
     * 实名认证，可暂时跳过不认证
     * @param email
     * @param realName
     * @param realId
     * @return
     */
    @RequestMapping("/regInfo3")
    @ResponseBody
    public String regInfo3(String email,String realName,String realId){

        User user = new User();
        user.setUserRealName(realName);
        user.setUserRealId(realId);
        if(realName!=null&&realId!=null){
            user.setUserPerms("shiming");
        }
        userService.updateUser(user,email);

        return "regOk";
    }

    /**
     * 用户登录
     * 数据库查出账号密码，比对输入是否一致
     * @param email
     * @param password
     * @return
     */
    @RequestMapping("/userLogin")
    @ResponseBody
    public String userLogin(String email,String password,HttpSession session){

//        System.out.println("进了userLogin方法");

        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token=new UsernamePasswordToken(email,password);

        try {
            //执行登录的方法，如果没有异常就说明没问题
            subject.login(token);
//            return "index";
            return "loginOk";
        } catch (UnknownAccountException e) {
            //用户名不存在
//            session.setAttribute("msg","用户名错误");
//            return "login";
            return "error";
        }catch (IncorrectCredentialsException e){
            //密码不存在
//            session.setAttribute("msg","密码错误");
//            return "login";
            return "error";
        }

//        User user = new User();
//        user.setUserEmail(email);
//
//        User user1=userService.queryUserInfo(user);
//        System.out.println("登录查询出来的user:"+user1);
//
//        String userPassword=user1.getUserPassword();
//        if (password.equals(userPassword)){
//            //将查出来登录信息存入session
//
//            return "loginOk";
//        }else {
//            return "error";
//        }
    }

    /**
     * 去个人中心的首页
     * @return
     */
    @RequestMapping("/toUserIndex")
    public String toUserIndex(){
        return "userIndex";
    }

    /**
     * 去修改个人信息的主页
     * @return
     */
    @RequestMapping("/toUserUpdateInfo")
    public String toUserUpdateInfo(){
        return "userUpdateInfo";
    }

    /**
     * 去个人中心的修改密码
     * @return
     */
    @RequestMapping("/toUserChangePwd")
    public String toUserChangePwd(){
        return "userChangePwd";
    }

    /**
     * 修改用户密码
     * @param userId
     * @return
     */
    @RequestMapping("/changePwd")
    @ResponseBody
    public String changePwd(String userId,String userPassword){

        User user=new User();
        user.setUserId(userId);
        user.setUserPassword(userPassword);
        userService.updateUserByUserId(user);
        return "1";
    }

    /**
     * 去个人中心的交易记录
     * @return
     */
    @RequestMapping("/toUserJiaoYi")
    public String toUserJiaoYi(){
        return "userJiaoyi";
    }

    /**
     * 通过session中的userID查询出交易记录
     * @param request
     * @return
     */
    @RequestMapping("/getUserOrders")

    public String getUserOrders(HttpServletRequest request, HttpSession session){

        User user= (User) request.getSession().getAttribute("loginUser");
        String userId=user.getUserId();

        List<Orders> orders=ordersService.queryOrdersByUserId(userId);
        System.out.println(orders);

        session.setAttribute("orders",orders);

        return "userJiaoyi";

    }

    /**
     * 去个人中心的我的钱包
     * @return
     */
    @RequestMapping("/toUserQianBao")
    public String toUserQianBao(){
        return "userQianBao";
    }

    /**
     * 充值增加用户余额
     * @return
     */
    @RequestMapping("/rechargeAmount")
    public String rechargeAmount(String amount,HttpSession session){

        User loginUser= (User) session.getAttribute("loginUser");

        BigDecimal balance1=loginUser.getUserBalance();
        BigDecimal balance2=new BigDecimal(amount);
        BigDecimal balance3=balance1.add(balance2);

        User user=new User();
        user.setUserId(loginUser.getUserId());
        user.setUserBalance(balance3);

        userService.updateUserByUserId(user);
        return "rechargeOk";
    }
    /**
     * 消费减少用户余额
     * @return
     */
    @RequestMapping("/consumeAmount")
    public String consumeAmount(String amount,HttpSession session){

        User loginUser= (User) session.getAttribute("loginUser");

        BigDecimal balance1=loginUser.getUserBalance();
        BigDecimal balance2=new BigDecimal(amount);
        int res=balance1.compareTo(balance2);

        //余额不足
        if(String.valueOf(res).equals("-1")){
            return "balanceLow";
        } else if(String.valueOf(res).equals("1")){
            BigDecimal balance3=balance1.subtract(balance2);

            User user=new User();
            user.setUserId(loginUser.getUserId());
            user.setUserBalance(balance3);

            userService.updateUserByUserId(user);
            return "consumeOk";
        }else {
            return "error";
        }

    }


    /**
     * 去个人中心的实名认证
     * @return
     */
    @RequestMapping("/toUserShiMing")
    public String toUserShiMing(){
        return "userShiMing";
    }



    /**
     * 将前端传入的实名认证信息存入数据库
     * @param userRealName
     * @param userRealId
     * @param userEmail
     * @param vCode
     * @param session
     * @return
     */
    @RequestMapping("/addUserRealInfo")
    @ResponseBody
    public String addUserRealInfo(String userRealName,String userRealId,String userEmail,String vCode,HttpSession session){

        //检查邮箱，未修改则使用session中，修改则使用输入的新邮箱
        User user= (User) session.getAttribute("loginUser");
        if (userEmail==""||userEmail.equals("")){
            userEmail=user.getUserEmail();
        }

        //检查用户信息是否已经被注册
        User user1 = userService.selectUserRealInfo(userRealName, userRealId);
        System.out.println("查询出的真实用户信息："+user1);
        if(!(user1==null||user1.equals(""))){
            return "reg_error";
        }

        //通过用户邮箱在redis中查出验证码
        String userVCode=vCodeService.getValueByKey(userEmail);
        //比对用户输入对的验证码和查出来的是否一致
        if(vCode.equals(userVCode)){

            String userId=user.getUserId();
            User user2=new User();
            user2.setUserId(userId);
            user2.setUserRealName(userRealName);
            user2.setUserRealId(userRealId);
            user2.setUserPerms("shiming");

            userService.updateUserByUserId(user2);

            User user4=userService.queryUserByUserId(userId);
            session.setAttribute("loginUser",user4);

            return "shiming_ok";
        }else{
            return "vCode_error";
        }

    }

    /**
     * 去抽奖的页面
     * @return
     */
    @RequestMapping("/toVipLottery")
    public String toVipLottery(){
        return "lottery";
    }

    /**
     * 处理抽奖的结果
     * 生成抽奖的订单
     * @return
     */
    @RequestMapping("/lotteryRes")
    @ResponseBody
    public String lotteryRes(String res,HttpSession session){

        User loginUser= (User) session.getAttribute("loginUser");

        BigDecimal balance1=loginUser.getUserBalance();
        //每次抽奖的费用
        BigDecimal balance2=new BigDecimal(5);
        int res2=balance1.compareTo(balance2);

        //余额不足
        if(String.valueOf(res2).equals("-1")){
            return "balanceLow";
        } else if(String.valueOf(res2).equals("1")){
            BigDecimal balance3=balance1.subtract(balance2);

            User user2=new User();
            user2.setUserId(loginUser.getUserId());
            user2.setUserBalance(balance3);
            userService.updateUserByUserId(user2);

            //使用UUID作为order_number订单号
            UUID uuid = UUID.randomUUID();
            String uuid1 = uuid.toString();
            String order_number = uuid1.replaceAll("-", "");

            Orders orders=new Orders();
            orders.setOrderNo(order_number);
            orders.setUserId(loginUser.getUserId());
            orders.setStatus("1");
            orders.setCreateTime(new Date());
            orders.setTotalAmount(new BigDecimal(5));
            orders.setTradeType("抽奖");

            ordersService.addOrder(orders);

            //更新用户信息
            User user3=new User();
            user3.setUserEmail(loginUser.getUserEmail());
            User user4=userService.queryUserInfo(user3);
            session.setAttribute("loginUser",user4);



            return "consumeOk";
        }else {
            return "error";
        }


//        return null;
    }


    /**
     * 去魔方游戏的页面
     * @return
     */
    @RequestMapping("/toVipCube")
    public String toVipCube(){
        return "cube";
    }

    @RequestMapping("/toUserVip")
    public String toUserVip(){
        return "userVip";
    }

    /**
     * 充值vip添加时间
     * @param money
     * @param times
     * @return
     */
    @RequestMapping("/setVip")
    @ResponseBody
    public String setVip(Long times,String money,HttpSession session){

        User loginUser= (User) session.getAttribute("loginUser");
        String userId=loginUser.getUserId();

        BigDecimal balance1=loginUser.getUserBalance();
        BigDecimal balance2=new BigDecimal(money);
        int res=balance1.compareTo(balance2);

        //余额不足
        if(String.valueOf(res).equals("-1")){
            return "balanceLow";
        } else if(String.valueOf(res).equals("1")){
            BigDecimal balance3=balance1.subtract(balance2);

            //通过userId获取出是否在redis中存有vip信息
            Long times1=redisTemplate.getExpire(userId, TimeUnit.DAYS);
            String userVipDay;
            if (times1>0){
                Long times2=times1+times;
                userVipDay=String.valueOf(times2);
                redisTemplate.opsForValue().set(userId,"vip",times2, TimeUnit.DAYS);
            }else{
                redisTemplate.opsForValue().set(userId,"vip",times, TimeUnit.DAYS);
                userVipDay=String.valueOf(times);
            }

            User user=new User();
            user.setUserId(loginUser.getUserId());
            user.setUserBalance(balance3);
            user.setUserVipDay(userVipDay);
            userService.updateUserByUserId(user);

            //添加订单
            //使用UUID作为order_number订单号
            UUID uuid = UUID.randomUUID();
            String uuid1 = uuid.toString();
            String order_number = uuid1.replaceAll("-", "");

            Orders orders=new Orders();
            orders.setOrderNo(order_number);
            orders.setUserId(loginUser.getUserId());
            orders.setTradeType("会员充值");
            orders.setTotalAmount(balance2);
            orders.setStatus("1");
            orders.setCreateTime(new Date());
            ordersService.addOrder(orders);

            User loginUser2=userService.queryUserByUserId(userId);
            session.setAttribute("loginUser",loginUser2);


            return "consumeOk";
        }else {
            return "error";
        }


    }


}
