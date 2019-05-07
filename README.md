# Sun8min

Sun8min的个人项目，在线实例 [sun8min.com]，详见博客 [Blog]

---
## 功能简介

- 跨域名单点登入登出
- 分布式事务
- 支付宝PC/Wap端支付

---
## 项目准备

- [mysql-8.0.15]        端口: 3306(默认) 用户名: root 密码: 123123
- [redis-5.0.4]         端口: 6379(默认) 密码: 123123
- [zookeeper-3.4.13]    端口: 2181(默认)
- [fescar-server-0.4.0] 端口: 8091(启动时指定)
- [tomcat-9.0.19]       端口: 8080(默认)
- prepare/db_script/sun8min.sql 数据库脚本执行
- prepare/hosts/hosts 本地/etc/hosts文件追加内容

---
## 项目启动

- 启动 注册中心 [zookeeper-3.4.13] : `sh zookeeper-3.4.13/bin/zkServer.sh start`
- 启动 分布式事务支持 [fescar-server-0.4.0] : `sh fescar-server-0.4.0/bin/fescar-server.sh 8091 fescar-server-0.4.0/data/`
- 启动 nosql [redis-5.0.4] : `redis-server redis.conf`
- 启动 cas 服务端: sso模块执行`./build.sh package`，
  将/target下的cas.war用[tomcat-9.0.19]部署 : `bin/startup.sh`
- 启动 pay, account, order, product, user 微服务模块
- 启动 seckill 微服务模块

---
## 项目入口
http://www.app1.com:9600

---
## 联系方式

8minofsun@gmail.com

[sun8min.com]: https://www.sun8min.com
[Blog]: https://sun8min.github.io/
[zookeeper-3.4.13]: https://github.com/apache/zookeeper/releases/tag/release-3.4.13
[fescar-server-0.4.0]: https://github.com/seata/seata/releases/tag/v0.4.0
[redis-5.0.4]: https://github.com/antirez/redis/releases/tag/5.0.4
[tomcat-9.0.19]: https://github.com/apache/tomcat/releases/tag/9.0.19
[mysql-8.0.15]: https://dev.mysql.com/downloads/mysql/
