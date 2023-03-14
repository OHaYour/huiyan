# HuiYan

这是一个关于在线视频网站的SpringBoot项目

项目名称：汇眼（HuiYan）；意为汇聚有意思的视频，与独具慧眼，志同道合的朋友一同创作的视频网站。

##### 项目特色
1.调用支付宝的接口使得充值方式更加接近真实；

2.结合当下热点，调用百度地图的接口定位用户发出评论回复的实际地理位置；

3.页面的顶部，侧面菜单使用vue的组件思想，使用th:fragment;和th:insert；标签在每个页面上进行拼装，提高代码的复用率；

4.用户发表不当言论，被管理员警告到一定的次数后，会被打上“网暴者”的标签，当此用户在发表任何评论和回复时都会显示，起到震慑的作用。
  

# 演示预览

![image](https://github.com/OHaYour/testmd/blob/main/photo/photo01.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo02.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo03.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo04.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo05.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo06.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo07.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo08.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo09.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo10.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo11.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo12.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo13.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo14.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo15.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo16.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo17.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo18.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo19.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo20.png)
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo21.png)




# 安装环境及开发工具

JDK1.8

Idea、MySQL、Redis



# 项目的基本结构
![image](https://github.com/OHaYour/testmd/blob/main/photo/photo22.png)

# 使用方法

### 用户端（part1）

##### 视频首页：

点击视频会通过Shiro进行认证，若未登录则会跳转至登录界面



##### 登录界面：

若有账号则可直接登录，没有账号可点击注册

在输入框失去聚焦时会对其中的内容进行正则校验，不符合规则会添加提示语

从数据库查找完成之后会有一个滑动验证，验证完成之后再跳转至首页



##### 注册界面：

通过填写的邮箱，经由集成QQ邮箱发送验证码辅助注册，填写用户信息，实名认证

在输入框失去聚焦时会对其中的内容进行正则校验，数据库中查询是否已注册，不符合会添加提示语

注册完成跳转至登录界面



##### 首页操作：

点击视频进入视频详情播放

点击分类可查询相关内容

支持视频名称的模糊查询

点击上传视频

点击进入个人中心



##### 视频详情界面：

对视频进行播放、点赞、投币、评论、回复操作，点赞、投币可取消，

投币需要支付Y币，余额不足会跳转至个人中心的充值界面

通过调用百度地图接口对获取到的机器IP地址进行解析并显示在评论回复中

对用户进行的评论、回复进行关键字比对，若包含敏感词汇，则不予显示，进行弹窗警告，被警告次数加一。当被警告此时达到一定次数时，在其评论、回复时贴上 网暴者 标签

若用户未进行实名认证，跳转至用户中心进行实名认证



##### 上传视频界面：

用户自主填写视频信息，上传视频封面及视频文件，通过IO流将文件写入项目静态资源以供调用



##### 个人中心界面：

首页显示我的信息，提供修改信息，实名认证，修改密码的界面

点击进入我的钱包、会员充值、交易记录界面

提供会员专区的抽奖和魔方小游戏



##### 我的钱包界面：

显示账户余额，调用支付宝接口进行拟真支付。

在点击充值时创建充值订单，若支付成功，则正常存入数据库；若未成功支付，则修改订单状态，不予成功充值改变账户余额。



##### 会员充值界面：

显示会员剩余天数，从Redis中读出；提供不同天数的会员充值，扣除一定账户余额充值会员天数。

充值成功天数信息存入Redis，为用户添加会员权限。



##### 抽奖、魔方小游戏界面：

进入该界面调用Shiro认证是否具有会员权限，若没有，则跳转至会员充值界面。



### 管理员端（part2）

##### 用户管理界面：

显示用户的头像、昵称、实名认证、警告次数等信息，可对其进行管理



##### 视频管理界面：

可观看视频视频进行审核，不通过的可给出修改意见

可进行上传电影的操作



##### 评论管理：

可对包含敏感词汇的评论回复进行人工审核

可自行设置敏感词汇








