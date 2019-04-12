## XSS
###### [wiki链接](https://zh.wikipedia.org/wiki/跨網站指令碼)
- 跨站脚本（英语：Cross-site scripting，通常简称为：XSS）
- 是一种网站应用程序的安全漏洞攻击，是代码注入的一种。
- 它允许恶意用户将代码注入到网页上，其他用户在观看网页时就会受到影响。
- 这类攻击通常包含了HTML以及用户端脚本语言。

ps: 本应为CSS，但因为与网页设计CSS样式（Cascading Style Sheets）冲突，
所以将Cross（意为“交叉”）改以交叉形的X做为缩写。

###### example:
```html
<html>
    <head>
       <title>XSS测试</title>
    </head>
<body>
<div>
    <!-- 假设这里是其他用户输入的内容 -->
    <script>alert("XSS!")</script>
    <script>alert(document.cookie)</script>
</div>
</body>
</html>
```

#### 主要危害：
1. 盗用cookie，获取敏感信息。
3. 利用iframe、frame、XMLHttpRequest或上述Flash等方式，以
  （被攻击）用户的身份执行一些管理动作，或执行一些一般的如发微博、
   加好友、发私信等操作。
4. 利用可被攻击的域受到其他域信任的特点，以受信任来源的身份请求一
   些平时不允许的操作，如进行不当的投票活动。
5. 在访问量极大的一些页面上的XSS可以攻击一些小型网站，实现DDoS
   攻击的效果。

#### 主要解决办法：
1. 将用户所提供的内容进行过滤，例如Java的xssprotect (Open Source Library)。
2. 针对cookie：可以设置cookie的HttpOnly属性，指示浏览器只能在http/https
   请求下使用cookie，js无法获取操作cookie