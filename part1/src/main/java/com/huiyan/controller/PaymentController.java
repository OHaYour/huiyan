package com.huiyan.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;

import com.huiyan.config.AlipayConfig;
import com.huiyan.pojo.Orders;
import com.huiyan.pojo.User;
import com.huiyan.service.AlipayService;
import com.huiyan.service.OrdersService;
import com.huiyan.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private UserService userService;



    /**
     * 直接购买商品逻辑
     * @param response
     * @param request
     */

    @RequestMapping(value = "/pay")
    @ResponseBody
    public void payMent(String money, HttpServletResponse response, HttpServletRequest request) {


        try {

            response.setContentType("text/html;charset=utf-8");

            //从session中获取userId
            User user= (User) request.getSession().getAttribute("loginUser");
            String userId= user.getUserId();



            //调用支付宝支付
            PrintWriter out = response.getWriter();
            //获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
            //设置请求参数
            AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
            aliPayRequest.setReturnUrl(AlipayConfig.return_url);
            aliPayRequest.setNotifyUrl(AlipayConfig.notify_url);

            //使用UUID作为order_number订单号
            UUID uuid = UUID.randomUUID();
            String uuid1 = uuid.toString();
            String order_number = uuid1.replaceAll("-", "");

            //付款金额，从前台获取，必填
            String total_amount = new String(money);
            //将付款金额存入session作用域
            request.getSession().setAttribute("totalAmount",total_amount);


            //订单名称
            String subject = new String("系统充值");

            //往订单表里加数据，默认未支付
            Orders orders = new Orders();
            orders.setOrderNo(order_number);
            orders.setTradeType(subject);
            BigDecimal totalAmount = new BigDecimal(total_amount);
            orders.setTotalAmount(totalAmount);
            orders.setCreateTime(new Date());
            orders.setStatus("0");
            orders.setUserId(userId);

            ordersService.addOrder(orders);


            aliPayRequest.setBizContent("{\"out_trade_no\":\"" + order_number + "\","
                    + "\"total_amount\":\"" + total_amount + "\","
                    + "\"subject\":\"" + subject + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            //请求
            String result = null;

            result = alipayClient.pageExecute(aliPayRequest).getBody();

            //输出
            out.println(result);
            log.info("返回结果={}", result);


        }catch (IOException e) {
            e.printStackTrace();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 支付宝同步通知页面,成功返回
     * @param request
     * @param response
     * @param httpSession
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alipayReturnNotice")
    public String alipayReturnNotice(HttpServletRequest request, HttpServletRequest response, HttpSession httpSession) throws Exception {
        log.info("支付成功, 进入同步通知接口...");
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        log.info("支付宝返回参数："+params);
        String OrderNo=params.get("out_trade_no");

        //将订单表中的订单状态修改为已支付
        Orders orders=new Orders();
        orders.setOrderNo(OrderNo);
        orders.setStatus("2");
        ordersService.updataStatus(orders);

        //取出存入session中的值，加到余额中去
        String balance= (String) request.getSession().getAttribute("totalAmount");
        User loginUser= (User) request.getSession().getAttribute("loginUser");

        BigDecimal balance1=loginUser.getUserBalance();
        BigDecimal balance2=new BigDecimal(balance);
        BigDecimal balance3=balance1.add(balance2);

        User user=new User();
        user.setUserId(loginUser.getUserId());
        user.setUserBalance(balance3);

        userService.updateUserByUserId(user);

        User user2=new User();
        user2.setUserEmail(loginUser.getUserEmail());
        User user3=userService.queryUserInfo(user2);
        httpSession.setAttribute("loginUser",user3);


//        Student student= (Student) request.getSession().getAttribute("student");
//        String studentId=student.getStudentId();
//        studentService.updateStudentBalance(studentId,balance);

        // 调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
        if(signVerified) {
            //商户订单号
            String orderNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            //alipayService.onlyBuy(orderNo);

            log.info("******************** 支付成功(支付宝同步通知) ********************");
            log.info("* 订单号: {}", orderNo);
            log.info("* 支付宝交易号: {}", trade_no);
            log.info("* 实付金额: {}", total_amount);
            log.info("***************************************************************");

        }else{
            log.error("同步回调签名验证失败");
        }
        return "userQianBao";
    }

    @RequestMapping("/check")
    @ResponseBody
    public String check(String money){

        //通过“.”分隔判断输入的money长度
        String []moneys= money.split("\\.");
        if (moneys.length==1){
            return "1";
        }else if (moneys.length>2){
            return "0";
        }else{
            //判断分隔后最后一段小数的长度
            if( moneys[1].length()>2 ){
                return "0";
            }else{
                return "1";
            }
        }

    }


}
