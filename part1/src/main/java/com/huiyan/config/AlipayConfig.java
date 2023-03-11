package com.huiyan.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 */
public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static final String app_id = "2021000117633403";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static final String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCr/lp7JSCxgtMXNC7o92C7TiwhThTY7iH9abPSsAKMiYm3M4zSPLBu5xqJLp7RDPjPcmsNgbFe1B3v2L0XYNndth7RI/JrLFKM5ruh9+sFxBGwn8W4CMQIlFXMHB6Dj3ozFIzHTrH2tSx8KR8eQlQb5aOxw4SruEzi3/Fl0t1KkT0TFcHRHnzXhAcPlDI591sx5ZHtc8FVtavWmUi4bKSjF0QOY3uv06SNwNu0WZoWZRBIXtRcTX9E88ckzFiOiXdjiLuzxCMR9XRsHZEM0KK/ZgNimjlgNXAtIc2M2E5lOiqgiZUfB+nAm8Sop4VgH289y5zgjaI+6alzR7E0LpR/AgMBAAECggEBAIUaXK98diap1e7ZJYyJFUUfJCvKH3waHhfdW5qrN+3G6trqfAqfiPLqyrBrceDapxrzs0FKoxd+FITvOLcE7tYUARldNlgWfvQoBAE+uZVGvWHw47SepAb3tMY25K5vtOQe/SupDTSd1QVpVXm3XxwPxXTmmJ/EC0xwfoKMg1EWe7segrdaP7Z01dWmzK1zwXv2F3URvLMd6TsBG5j9d8c2gk4r/3zmNtoXJKPtUIt4+3h3e8NDbn1lEQNtfRIbjJUGBNR3mSJHaYdJb7KJ4W4a8uDBlvSyVk5EM/VKKjDuNxPTp/PzyeocJ9p+rZN+WLnwKOWtNpfW6e9MkbknMtECgYEA36ObOrXb5isOnH3nkExqoRBq3RIRT9SYFyhwS1/vL6ESV5MNo/4PGOr82FsCAsXRf7ccSqaroafmv8K6HscxS6zWqY3Ng8DfCkXZVJhJshxZhbSDqfQHy3YcukPmJkyAEyNHR3e8YJfa83QPWp/Z+meCsnGRYuaBBEud63v6oEsCgYEAxOGco8RY2lO4dLjhWN5vbTKsXew6JSlQ4cqqspyTYS2EbS4Q/wKR5mDn21tumvmrr+UlFFdCA7po1L5wMBG93+/7XJECS1ELXyRxz23OUnfMvF8nFlddq6hCIQRJ+diAEMF1FRz5ZKVdryQ6SGY5V1utGDxMMExl7kMZU9GTxB0CgYBZstdaDwMqKG7t2Zn9SIkkHN+f19VVvaFa+H9B3DKlWedvdK3t7sLDbc0IQkhmME28+gbA8mS4rA1jMIlSOvbMsYBP9W5OwBc1mZp30bUNWU2W0TZV/mj59F/FXeJ7zgc4pde7FI1b+Mx7BTv7T04wnz/OrrpvfqCUxneKseluTQKBgQC4yWSilNwsdt2uuAPRzFxa6m+7a8ebL28ZrYWPRMRTNkyPyOfs4TWdPCEB0ow6atHIstKdh9SO+TwBSYi0pUJIV87dr8Cufi+lKeikHtTCtGx19P0TzAY/4hnHMWZZWOnCUEEbAunUnB0VEHSflUqGgqYxFA8SWfJlsZapBBCjxQKBgF9c6j3bj/Z8UaqOxivSY95YC+6ohSCXFp7LNBwdq+4nVwQ6hn4c60Jej6rw383sxRhSdhcCCwJqVDVTNk2Qjbz2OVQmFDJEgW1+drpmPkkJqHOSZpfcZovaz0UoHGbuW5QxXOS/lMOm/38J8yVT0SjbjSGhJbH6+jP3xj2GFsAY\n";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static final String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq/5aeyUgsYLTFzQu6Pdgu04sIU4U2O4h/Wmz0rACjImJtzOM0jywbucaiS6e0Qz4z3JrDYGxXtQd79i9F2DZ3bYe0SPyayxSjOa7offrBcQRsJ/FuAjECJRVzBweg496MxSMx06x9rUsfCkfHkJUG+WjscOEq7hM4t/xZdLdSpE9ExXB0R5814QHD5QyOfdbMeWR7XPBVbWr1plIuGykoxdEDmN7r9OkjcDbtFmaFmUQSF7UXE1/RPPHJMxYjol3Y4i7s8QjEfV0bB2RDNCiv2YDYpo5YDVwLSHNjNhOZToqoImVHwfpwJvEqKeFYB9vPcuc4I2iPumpc0exNC6UfwIDAQAB\n";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static final String notify_url = "http://localhost:8081/index.html";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static final String return_url = "http://localhost:8081/payment/alipayReturnNotice";

    public static final String return_url2 = "http://localhost:8081/payment/alipayReturnNotice2";

	// 签名方式
	public static final String sign_type = "RSA2";
	
	// 字符编码格式
	public static final String charset = "utf-8";

    public final static String FORMAT = "JSON";// 返回格式
	
	// 支付宝网关
	public static final String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static final String log_path = "D:\\沙箱支付宝";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

