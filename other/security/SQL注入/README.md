## SQL注入
###### [wiki链接](https://zh.wikipedia.org/wiki/SQL注入)
- SQL注入（英语：SQL injection），也称SQL注入或SQL注码，
- 是发生于应用程序与数据库层的安全漏洞。

###### example:
以一个网页有两个字段让用户输入用户名与密码为例：
```sql
SELECT UserList.Username
FROM UserList
WHERE UserList.Username = 'Username'
AND UserList.Password = 'Password'
```
如果恶意在密码栏注入某些合法代码 ("password' OR '1'='1")，查询结果便如下所示：
```sql
SELECT UserList.Username
FROM UserList
WHERE UserList.Username = 'Username'
AND UserList.Password = 'password' OR '1'='1'
```
在上面示例里，"'1'='1'"逻辑式将永远为真。

#### 主要危害：
1. 数据表中的数据外泄，例如企业及个人机密数据，账户数据，密码等。
2. 破坏硬盘数据，瘫痪全系统（例如xp_cmdshell "FORMAT C:"）。


#### 主要解决办法：
1. 在设计应用程序时，完全使用参数化查询（Parameterized Query）来设计数据访问功能。
2. 在组合SQL字符串时，先针对所传入的参数加入其他字符（将单引号字符前加上转义字符）。
```text
所谓参数化是指预编译，编译是分析sql语句，找出关键字，完成语法分析，生成执行计划。
因为完成了语法分析，所以后面传入的任何字符都不会作为sql关键字，只能作为字面量参数。
```