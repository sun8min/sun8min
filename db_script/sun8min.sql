-- 用户库
drop database if exists sun8min_user;
create database sun8min_user default charset utf8mb4 collate utf8mb4_unicode_ci;
use sun8min_user;
-- -- 用户表
drop table if exists `user`;
create table `user`
(
  user_id        bigint unsigned auto_increment comment '用户id',
  user_nick_name varchar(16) not null comment '用户显示名',
  user_real_name varchar(16) not null default '' comment '用户真实名',
  user_phone     varchar(32) not null default '' comment '用户手机号',
  user_sex       tinyint     not null default '0' comment '用户性别（0:未知，1:男，2:女）',
  gmt_create     datetime    not null default current_timestamp comment '创建时间',
  gmt_modified   datetime    not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted     tinyint     not null default 0 comment '是否删除（0:正常，1:已删）',
  primary key (user_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment '用户表';

-- 账户库
drop database if exists sun8min_capital;
create database sun8min_capital default charset utf8 collate utf8_unicode_ci;
use sun8min_capital;
-- -- 账户表
drop table if exists capital;
create table capital
(
  capital_id          bigint unsigned auto_increment comment '账户id',
  user_id             bigint unsigned not null comment '用户id',
  capital_amount      decimal(10,2) unsigned not null comment '账户余额合计（精确到分）',
  gmt_create          datetime        not null default current_timestamp comment '创建时间',
  gmt_modified        datetime        not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted          tinyint         not null default 0 comment '是否删除（0:正常，1:已删）',
  primary key (capital_id),
  unique key uk_user_id (user_id)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '用户账户表';
-- -- 账户交易表
drop table if exists capital_trade_order;
create table capital_trade_order
(
  capital_trade_order_id bigint unsigned auto_increment comment '账户交易id',
  from_user_id                bigint unsigned not null comment '资金转出用户id',
  to_user_id                  bigint unsigned not null comment '资金转入用户id',
  capital_trade_amount        decimal(10,2) unsigned not null comment '交易金额合计（精确到分）',
  trade_order_no              varchar(32)     not null comment '订单交易号',
  gmt_create                  datetime        not null default current_timestamp comment '创建时间',
  gmt_modified                datetime        not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted                  tinyint         not null default 0 comment '是否删除（0:正常，1:已删）',
  primary key (capital_trade_order_id),
  unique key uk_trade_order_no (trade_order_no)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '用户账户交易表';

-- 红包库
drop database if exists sun8min_redpacket;
create database sun8min_redpacket default charset utf8 collate utf8_unicode_ci;
use sun8min_redpacket;
-- -- 红包表
drop table if exists redpacket;
create table redpacket
(
  redpacket_id          bigint unsigned auto_increment comment '红包id',
  user_id               bigint unsigned not null comment '用户id',
  redpacket_amount      decimal(10,2) unsigned not null comment '红包余额合计（精确到分）',
  gmt_create            datetime        not null default current_timestamp comment '创建时间',
  gmt_modified          datetime        not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted            tinyint         not null default 0 comment '是否删除（0:正常，1:已删）',
  primary key (redpacket_id),
  unique key uk_user_id (user_id)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '用户红包表';
-- -- 红包交易表
drop table if exists redpacket_trade_order;
create table redpacket_trade_order
(
  redpacket_trade_order_id      bigint unsigned auto_increment comment '红包交易id',
  from_user_id                  bigint unsigned not null comment '资金转出用户id',
  to_user_id                    bigint unsigned not null comment '资金转入用户id',
  redpacket_trade_amount        decimal(10,2) unsigned not null comment '交易金额合计（精确到分）',
  trade_order_no                varchar(32)     not null comment '订单交易号',
  gmt_create                    datetime        not null default current_timestamp comment '创建时间',
  gmt_modified                  datetime        not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted                    tinyint         not null default 0 comment '是否删除（0:正常，1:已删）',
  primary key (redpacket_trade_order_id),
  unique key uk_trade_order_no (trade_order_no)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '用户红包交易表';

-- 订单库
drop database if exists sun8min_order;
create database sun8min_order default charset utf8 collate utf8_unicode_ci;
use sun8min_order;
-- -- 订单表
drop table if exists `order`;
create table `order`
(
  order_id               bigint unsigned auto_increment comment '订单id',
  from_user_id           bigint unsigned not null comment '资金转出用户id',
  to_user_id             bigint unsigned not null comment '资金转入用户id',
  capital_trade_amount   decimal(10,2) unsigned not null comment '账户交易金额合计（精确到分）',
  redpacket_trade_amount decimal(10,2) unsigned not null comment '红包交易金额合计（精确到分）',
  order_status           tinyint         not null comment '订单支付状态（0:初始化，1:支付中，2:支付成功，3:支付失败，4:取消支付）',
  trade_order_no         varchar(32)     not null comment '订单交易号',
  gmt_create             datetime        not null default current_timestamp comment '创建时间',
  gmt_modified           datetime        not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted             tinyint         not null default 0 comment '是否删除（0:正常，1:已删）',
  primary key (order_id),
  unique key uk_trade_order_no (trade_order_no)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '订单表';
-- -- 订单商品项表
drop table if exists order_line;
create table order_line
(
  order_line_id      bigint unsigned auto_increment comment '订单商品项id',
  product_id         bigint unsigned not null comment '商品id',
  product_price      decimal(10,2) unsigned not null comment '商品价格（精确到分）',
  product_quantity   bigint unsigned not null comment '商品数量',
  trade_order_no     varchar(32)     not null comment '主订单交易号',
  gmt_create         datetime        not null default current_timestamp comment '创建时间',
  gmt_modified       datetime        not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted         tinyint         not null default 0 comment '是否删除（0:正常，1:已删）',
  primary key (order_line_id),
  unique key uk_trade_order_no_product_id (trade_order_no, product_id)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '订单商品项表';

-- 商店库
drop database if exists sun8min_shop;
create database sun8min_shop default charset utf8mb4 collate utf8mb4_unicode_ci;
use sun8min_shop;
-- -- 商店表
drop table if exists shop;
create table shop
(
  shop_id      bigint unsigned auto_increment comment '商店id',
  shop_name    varchar(16)     not null comment '商店名',
  user_id      bigint unsigned not null comment '所属用户id',
  gmt_create   datetime        not null default current_timestamp comment '创建时间',
  gmt_modified datetime        not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted   tinyint         not null default 0 comment '是否删除（0:正常，1:已删）',
  primary key (shop_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment '商店表';
-- -- 商品表
drop table if exists product;
create table product
(
  product_id               bigint unsigned auto_increment comment '商品id',
  product_name             varchar(16)     not null comment '商品名',
  product_image            varchar(128)    not null default '' comment '商品图片url',
  product_price            decimal(10,2) unsigned not null comment '商品售价（精确到分）',
  product_discount_type    tinyint         not null default 0 comment '商品折扣类型（0：无折扣，1：输入折后价，2：输入折扣百分比）',
  product_discount_price   decimal(10,2) unsigned comment '商品折后价（精确到分）',
  product_discount_percent tinyint unsigned comment '商品折扣百分比',
  shop_id                  bigint unsigned not null comment '所属商店id',
  gmt_create               datetime        not null default current_timestamp comment '创建时间',
  gmt_modified             datetime        not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted               tinyint         not null default 0 comment '是否删除（0:正常，1:已删）',
  primary key (product_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment '商品表';

-- ###################################
-- 基础数据插入

-- 用户库
use sun8min_user;
-- -- 用户表
insert into `user`(user_nick_name) values ('AAA'), ('BBB'), ('CCC');

-- 账户库
use sun8min_capital;
-- -- 账户表
insert into `capital`(user_id, capital_amount) values (1, 100000), (2, 100000), (3, 100000);

-- 红包库
use sun8min_redpacket;
-- -- 红包表
insert into `redpacket`(user_id, redpacket_amount) values (1, 50000), (2, 50000), (3, 50000);

-- 商店库
use sun8min_shop;
-- 商店表
insert into `shop`(shop_name, user_id) values ('AAA的小店', 1), ('BBB的小店', 2);
-- -- 商品表
insert into `product`(product_name, product_price, shop_id) values ('A1', 1, 1), ('A2', 10, 1), ('A3', 100, 1), ('A4', 1000, 1), ('A5', 10000, 1);
insert into `product`(product_name, product_price, shop_id) values ('B1', 1, 2), ('B2', 10, 2), ('B3', 100, 2), ('B4', 1000, 2), ('B5', 10000, 2);