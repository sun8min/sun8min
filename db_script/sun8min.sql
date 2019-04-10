-- 默认字符集utf8mb4，排序规则utf8mb4_unicode_ci，是为了兼容emoji表情
-- is_(?!deleted).* ,正则使用零宽度断言(?!exp)查找以is_开头不含deleted的字段

-- 用户库
drop database if exists sun8min_user;
create database sun8min_user default charset utf8mb4 collate utf8mb4_unicode_ci;
use sun8min_user;
-- -- 用户表
drop table if exists sun8min_user;
create table sun8min_user
(
  user_id         bigint unsigned auto_increment comment '用户id',
  user_nick_name  varchar(16)         not null comment '用户显示名',
  user_real_name  varchar(16)         not null default '' comment '用户真实名',
  user_phone      varchar(32)         not null default '' comment '用户手机号',
  user_sex        tinyint             not null default 0 comment '用户性别（0:未知，1:男，2:女）',
  extension_field varchar(255)        not null default '' comment '扩展字段（json格式）',
  version         int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create      datetime            not null default current_timestamp comment '创建时间',
  gmt_modified    datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted      tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (user_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment '用户表';
-- -- 购物车表
drop table if exists sun8min_cart;
create table sun8min_cart
(
  cart_id             bigint unsigned auto_increment comment '购物车id',
  user_id             bigint unsigned     not null comment '用户id',
  product_id          bigint unsigned     not null comment '商品id',
  product_quantity    int unsigned        not null default 1 comment '商品数量',
  product_snapshot_id int unsigned        not null comment '商品快照id，ps：可用于通知价格、活动变动',
  extension_field     varchar(255)        not null default '' comment '扩展字段（json格式）',
  version             int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create          datetime            not null default current_timestamp comment '创建时间',
  gmt_modified        datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted          tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (cart_id)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '购物车表';

-- 账户库
drop database if exists sun8min_account;
create database sun8min_account default charset utf8 collate utf8_unicode_ci;
use sun8min_account;
-- -- 账户表
drop table if exists sun8min_account;
create table sun8min_account
(
  account_id      bigint unsigned auto_increment comment '账户id',
  account_amount  decimal(10, 2) unsigned not null comment '账户余额合计（精确到分）',
  user_id         bigint unsigned         not null comment '用户id',
  extension_field varchar(255)            not null default '' comment '扩展字段（json格式）',
  version         int unsigned            not null default 0 comment '版本号（用于乐观锁）',
  gmt_create      datetime                not null default current_timestamp comment '创建时间',
  gmt_modified    datetime                not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted      tinyint(1) unsigned     not null default 0 comment '是否删除（0：否，1：是）',
  primary key (account_id),
  unique key uk_user_id (user_id)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '账户表';
-- -- 账户交易表
drop table if exists sun8min_account_trade_order;
create table sun8min_account_trade_order
(
  account_trade_order_id bigint unsigned auto_increment comment '账户交易id',
  user_id                bigint unsigned         not null comment '用户id',
  account_trade_amount   decimal(10, 2) unsigned not null comment '交易金额合计（精确到分）',
  trade_order_no         varchar(32)             not null comment '订单交易号',
  trade_type             tinyint unsigned        not null comment '交易类型（1：转出，2：转入）',
  extension_field        varchar(255)            not null default '' comment '扩展字段（json格式）',
  version                int unsigned            not null default 0 comment '版本号（用于乐观锁）',
  gmt_create             datetime                not null default current_timestamp comment '创建时间',
  gmt_modified           datetime                not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted             tinyint(1) unsigned     not null default 0 comment '是否删除（0：否，1：是）',
  primary key (account_trade_order_id),
  unique key uk_user_id_trade_order_no_trade_type (user_id, trade_order_no, trade_type)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '账户交易表';
-- -- fescar undo log
drop table if exists `undo_log`;
create table `undo_log`
(
  `id`            bigint(20)   not null auto_increment,
  `branch_id`     bigint(20)   not null,
  `xid`           varchar(100) not null,
  `rollback_info` longblob     not null,
  `log_status`    int(11)      not null,
  `log_created`   datetime     not null,
  `log_modified`  datetime     not null,
  `ext`           varchar(100) default null,
  primary key (`id`),
  unique key `ux_undo_log` (`xid`, `branch_id`)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment 'fescar_undo_log';

-- 订单库
drop database if exists sun8min_order;
create database sun8min_order default charset utf8 collate utf8_unicode_ci;
use sun8min_order;
-- -- 订单表
drop table if exists sun8min_order;
create table sun8min_order
(
  order_id           bigint unsigned auto_increment comment '订单id',
  from_user_id       bigint unsigned         not null comment '资金转出用户id',
  to_user_id         bigint unsigned         not null comment '资金转入用户id',
  shop_id            bigint unsigned         not null comment '商店id',
  order_trade_amount decimal(10, 2) unsigned not null comment '交易金额合计（精确到分）',
  order_pay_channel  tinyint unsigned        not null comment '订单支付渠道（1：账户，2：支付宝，3：微信）',
  order_pay_no       varchar(64)             not null default '' comment '渠道支付单号',
  order_pay_time     datetime comment '支付时间',
  order_status       tinyint unsigned        not null comment '订单状态（0:初始化，1：等待支付，2:支付中，3:支付成功，4:支付失败，5:取消支付，6：支付超时被系统关闭）',
  trade_order_no     varchar(32)             not null comment '订单交易号',
  extension_field    varchar(255)            not null default '' comment '扩展字段（json格式）',
  version            int unsigned            not null default 0 comment '版本号（用于乐观锁）',
  gmt_create         datetime                not null default current_timestamp comment '创建时间',
  gmt_modified       datetime                not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted         tinyint(1) unsigned     not null default 0 comment '是否删除（0：否，1：是）',
  primary key (order_id),
  unique key uk_trade_order_no (trade_order_no)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '订单表';
-- -- 订单商品项表
drop table if exists sun8min_order_line;
create table sun8min_order_line
(
  order_line_id       bigint unsigned auto_increment comment '订单商品项id',
  product_snapshot_id bigint unsigned     not null comment '商品快照id',
  product_quantity    int unsigned        not null comment '商品数量',
  trade_order_no      varchar(32)         not null comment '订单交易号',
  extension_field     varchar(255)        not null default '' comment '扩展字段（json格式）',
  version             int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create          datetime            not null default current_timestamp comment '创建时间',
  gmt_modified        datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted          tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (order_line_id),
  unique key uk_trade_order_no_product_snapshot_id (trade_order_no, product_snapshot_id)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '订单商品项表';
-- -- fescar undo log
drop table if exists `undo_log`;
create table `undo_log`
(
  `id`            bigint(20)   not null auto_increment,
  `branch_id`     bigint(20)   not null,
  `xid`           varchar(100) not null,
  `rollback_info` longblob     not null,
  `log_status`    int(11)      not null,
  `log_created`   datetime     not null,
  `log_modified`  datetime     not null,
  `ext`           varchar(100) default null,
  primary key (`id`),
  unique key `ux_undo_log` (`xid`, `branch_id`)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment 'fescar_undo_log';

-- 商品库
drop database if exists sun8min_product;
create database sun8min_product default charset utf8mb4 collate utf8mb4_unicode_ci;
use sun8min_product;
-- -- 品牌表
drop table if exists sun8min_brand;
create table sun8min_brand
(
  brand_id        bigint unsigned auto_increment comment '品牌id',
  brand_name      varchar(64)         not null default '' comment '品牌名',
  brand_image     varchar(128)        not null default '' comment '品牌图片存储（不含域名）',
  brand_desc      varchar(255)        not null default '' comment '品牌描述',
  extension_field varchar(255)        not null default '' comment '扩展字段（json格式）',
  version         int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create      datetime            not null default current_timestamp comment '创建时间',
  gmt_modified    datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted      tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (brand_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment '品牌表';
-- -- 类目表
drop table if exists sun8min_category;
create table sun8min_category
(
  category_id        bigint unsigned auto_increment comment '类目id',
  category_name      varchar(32)         not null comment '类目名',
  category_pid       bigint unsigned     not null comment '父id',
  category_rid       bigint unsigned     not null comment '根节点id',
  is_leaf            tinyint(1) unsigned not null default 0 comment '是否叶子节点（0：否，1：是）',
  is_contain_mapping tinyint(1) unsigned not null default 0 comment '是否包含映射（0：否，1：是，ps：是即需要去关联表找子项）',
  category_level     tinyint unsigned    not null comment '类目层级（从0开始）',
  category_type      tinyint unsigned    not null comment '类目类型（1：后台类目，2：前台类目）',
  category_sort      bigint unsigned     not null comment '排序id（默认应该和主键相同,调整排序好修改）',
  category_image     varchar(128)        not null default '' comment '类目图片存储（不含域名）',
  extension_field    varchar(255)        not null default '' comment '扩展字段（json格式）',
  version            int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create         datetime            not null default current_timestamp comment '创建时间',
  gmt_modified       datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted         tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (category_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment '类目表';
-- -- 类目类目关联表
drop table if exists sun8min_category_category;
create table sun8min_category_category
(
  category_category_id bigint unsigned auto_increment comment '类目类目id',
  parent_category_id   bigint unsigned     not null comment '类目id',
  child_category_id    bigint unsigned     not null comment '子类目id',
  extension_field      varchar(255)        not null default '' comment '扩展字段（json格式）',
  version              int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create           datetime            not null default current_timestamp comment '创建时间',
  gmt_modified         datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted           tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (category_category_id)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '类目类目关联表';
-- -- spu表
drop table if exists sun8min_spu;
create table sun8min_spu
(
  spu_id          bigint unsigned auto_increment comment 'spu_id',
  spu_name        varchar(16)         not null comment 'spu名',
  spu_image       varchar(128)        not null default '' comment 'spu图片存储（不含域名）',
  spu_video       varchar(128)        not null default '' comment 'spu视频存储（不含域名）',
  brand_id        bigint unsigned     not null comment '品牌id',
  category_id     bigint unsigned     not null comment '类目id',
  extension_field varchar(255)        not null default '' comment '扩展字段（json格式）',
  version         int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create      datetime            not null default current_timestamp comment '创建时间',
  gmt_modified    datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted      tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (spu_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment 'spu表';
-- -- 属性key表
drop table if exists sun8min_property_key;
create table sun8min_property_key
(
  property_key_id   bigint unsigned auto_increment comment '属性key_id',
  property_key_name varchar(16)         not null comment '属性key名',
  property_key_type tinyint unsigned    not null default 0 comment '属性key类型（0：未知，1：关键属性，2：非关键属性，3：销售属性，ps：影响具体商品价格、库存的即销售属性，例如：手机容量，而关键属性即能确定spu的属性，例如：手机型号，其他即非关键属性，例如：手机毛重）',
  property_key_sort bigint unsigned     not null comment '排序id（默认应该和主键相同,调整排序好修改）',
  is_required       tinyint(1) unsigned not null default 1 comment '是否必填（0：否，1：是）',
  category_id       bigint unsigned     not null comment '类目id',
  extension_field   varchar(255)        not null default '' comment '扩展字段（json格式）',
  version           int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create        datetime            not null default current_timestamp comment '创建时间',
  gmt_modified      datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted        tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (property_key_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment '属性key表';
-- -- 属性value表
drop table if exists sun8min_property_value;
create table sun8min_property_value
(
  property_value_id   bigint unsigned auto_increment comment '属性value_id',
  property_value_name varchar(16)         not null comment '属性value名',
  property_value_sort bigint unsigned     not null comment '排序id（默认应该和主键相同,调整排序好修改）',
  property_key_id     bigint unsigned     not null comment '属性key_id',
  extension_field     varchar(255)        not null default '' comment '扩展字段（json格式）',
  version             int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create          datetime            not null default current_timestamp comment '创建时间',
  gmt_modified        datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted          tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (property_value_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment '属性value表';
-- -- spu-关键非关键属性关联表
drop table if exists sun8min_spu_property;
create table sun8min_spu_property
(
  spu_property_id   bigint unsigned auto_increment comment 'spu-关键非关键属性关联id',
  spu_id            bigint unsigned     not null comment 'spu_id',
  property_key_id   bigint unsigned     not null comment '属性key值',
  property_value_id bigint unsigned     not null comment '属性value值',
  extension_field   varchar(255)        not null default '' comment '扩展字段（json格式）',
  version           int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create        datetime            not null default current_timestamp comment '创建时间',
  gmt_modified      datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted        tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (spu_property_id)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment 'spu-关键非关键属性关联表';
-- -- sku表
drop table if exists sun8min_sku;
create table sun8min_sku
(
  sku_id          bigint unsigned auto_increment comment 'sku_id',
  sku_name        varchar(16)         not null comment 'sku名',
  spu_id          bigint unsigned     not null comment 'spu_id',
  extension_field varchar(255)        not null default '' comment '扩展字段（json格式）',
  version         int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create      datetime            not null default current_timestamp comment '创建时间',
  gmt_modified    datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted      tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (sku_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment 'sku表';
-- -- sku-销售属性关联表
drop table if exists sun8min_sku_property;
create table sun8min_sku_property
(
  sku_property_id   bigint unsigned auto_increment comment 'sku-销售属性关联id',
  sku_id            bigint unsigned     not null comment 'sku_id',
  property_key_id   bigint unsigned     not null comment '属性key值',
  property_value_id bigint unsigned     not null comment '属性value值',
  extension_field   varchar(255)        not null default '' comment '扩展字段（json格式）',
  version           int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create        datetime            not null default current_timestamp comment '创建时间',
  gmt_modified      datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted        tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (sku_property_id)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment 'sku-销售属性关联表';
-- -- 商店表
drop table if exists sun8min_shop;
create table sun8min_shop
(
  shop_id         bigint unsigned auto_increment comment '商店id',
  shop_name       varchar(16)         not null comment '商店名',
  user_id         bigint unsigned     not null comment '所属用户id',
  extension_field varchar(255)        not null default '' comment '扩展字段（json格式）',
  version         int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create      datetime            not null default current_timestamp comment '创建时间',
  gmt_modified    datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted      tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (shop_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment '商店表';
-- -- 商品表
drop table if exists sun8min_product;
create table sun8min_product
(
  product_id               bigint unsigned auto_increment comment '商品id',
  product_name             varchar(16)             not null comment '商品名',
  sku_id                   bigint unsigned comment 'sku_id',
  product_measure_unit     tinyint unsigned        not null default 0 comment '计量单位（0：个，1：ml）',
  product_quantity         int unsigned            not null default 1 comment '商品数量',
  product_video            varchar(128)            not null default '' comment '商品视频存储（不含域名）',
  product_price            decimal(10, 2) unsigned not null comment '商品售价（精确到分）',
  product_discount_type    tinyint                 not null default 0 comment '商品折扣类型（0：无折扣，1：输入折后价，2：输入折扣百分比）',
  product_discount_price   decimal(10, 2) unsigned comment '商品折后价（精确到分）',
  product_discount_percent tinyint unsigned comment '商品折扣百分比',
  product_type             tinyint unsigned        not null default 0 comment '商品类型（0：单品，1：打包品，ps：非单品即需要去关联表找子项）',
  is_up_shelves            tinyint(1) unsigned     not null default 0 comment '是否上架（0：否，1：是）',
  is_visible               tinyint(1) unsigned     not null default 1 comment '是否展示（0：否，1：是）ps:保证可售卖而用户不可直接购买该单品，用例如：打包品中的单品，只能通过打包品买',
  shop_id                  bigint unsigned         not null comment '所属商店id',
  product_activity_flag    int unsigned            not null default 0 comment '商品活动标识（二进制位，为1表示是，右侧第1为1，1：秒杀，2：拼团，3：预售，4：团购，5：拍卖，ps:拼团是基于熟人之间的社交化电商传播，团购则是陌生人之间的传播）',
  extension_field          varchar(255)            not null default '' comment '扩展字段（json格式）',
  version                  int unsigned            not null default 0 comment '版本号（用于乐观锁）',
  gmt_create               datetime                not null default current_timestamp comment '创建时间',
  gmt_modified             datetime                not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted               tinyint(1) unsigned     not null default 0 comment '是否删除（0：否，1：是）',
  primary key (product_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment '商品表';
-- -- 商品快照表（记录价格、活动、上下架、删除变动）
drop table if exists sun8min_product_snapshot;
create table sun8min_product_snapshot
(
  product_snapshot_id      bigint unsigned auto_increment comment '商品快照id',
  product_id               bigint unsigned         not null comment '商品id',
  product_price            decimal(10, 2) unsigned not null comment '商品售价（精确到分）',
  product_discount_type    tinyint                 not null default 0 comment '商品折扣类型（0：无折扣，1：输入折后价，2：输入折扣百分比）',
  product_discount_price   decimal(10, 2) unsigned comment '商品折后价（精确到分）',
  product_discount_percent tinyint unsigned comment '商品折扣百分比',
  is_up_shelves            tinyint(1) unsigned     not null default 0 comment '是否上架（0：否，1：是）',
  is_visible               tinyint(1) unsigned     not null default 1 comment '是否展示（0：否，1：是）ps:保证可售卖而用户不可直接购买该单品，用例如：打包品中的单品，只能通过打包品买',
  product_activity_flag    int unsigned            not null default 0 comment '商品活动标识（二进制位，为1表示是，右侧第1为1，1：秒杀，2：拼团，3：预售，4：团购，5：拍卖，ps:拼团是基于熟人之间的社交化电商传播，团购则是陌生人之间的传播）',
  product_is_deleted       tinyint(1) unsigned     not null default 0 comment '商品是否删除（0：否，1：是）',
  extension_field          varchar(255)            not null default '' comment '扩展字段（json格式）',
  version                  int unsigned            not null default 0 comment '版本号（用于乐观锁）',
  gmt_create               datetime                not null default current_timestamp comment '创建时间',
  gmt_modified             datetime                not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted               tinyint(1) unsigned     not null default 0 comment '是否删除（0：否，1：是）',
  primary key (product_snapshot_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment '商品快照表（记录价格、活动、上下架、删除变动）';
-- -- 商品图片表
drop table if exists sun8min_product_image;
create table sun8min_product_image
(
  product_image_id   bigint unsigned auto_increment comment '商品图片id',
  product_image_name varchar(32)         not null default '' comment '商品图片名',
  product_id         bigint unsigned     not null comment '商品id',
  product_image      varchar(128)        not null default '' comment '商品图片存储（不含域名）',
  product_image_sort bigint unsigned     not null comment '排序id（默认应该和主键相同,调整排序好修改）',
  extension_field    varchar(255)        not null default '' comment '扩展字段（json格式）',
  version            int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create         datetime            not null default current_timestamp comment '创建时间',
  gmt_modified       datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted         tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (product_image_id)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  auto_increment = 1 comment '商品图片表';
-- -- 商品商品关联表
drop table if exists sun8min_product_product;
create table sun8min_product_product
(
  product_product_id bigint unsigned auto_increment comment '商品商品关联id',
  parent_product_id  bigint unsigned     not null comment '商品id',
  child_product_id   bigint unsigned     not null comment '子商品id',
  extension_field    varchar(255)        not null default '' comment '扩展字段（json格式）',
  version            int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create         datetime            not null default current_timestamp comment '创建时间',
  gmt_modified       datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted         tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (product_product_id)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '商品商品关联表';
-- -- 商品上架定时表
drop table if exists sun8min_product_schedule;
create table sun8min_product_schedule
(
  product_schedule_id      bigint unsigned auto_increment comment '商品上架定时id',
  schedule_type            tinyint unsigned    not null default 0 comment '定时任务类型（0：一次性[使用上下架时间]，1：周期性[使用cron表达式]）',
  gmt_up_shelves           datetime comment '上架时间',
  gmt_down_shelves         datetime comment '下架时间',
  cron_expression          varchar(32) comment 'cron表达式',
  cron_gmt_begin           datetime comment 'corn执行开始时间',
  cron_gmt_end             datetime comment 'corn执行结束时间',
  cron_up_shelves_duration bigint unsigned comment 'cron周期执行时，商品上架持续时间（单位：秒）',
  extension_field          varchar(255)        not null default '' comment '扩展字段（json格式）',
  version                  int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create               datetime            not null default current_timestamp comment '创建时间',
  gmt_modified             datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted               tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (product_schedule_id)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '商品上架定时表';
-- -- 库存表
drop table if exists sun8min_inventory;
create table sun8min_inventory
(
  inventory_id    bigint unsigned auto_increment comment '库存id',
  product_id      bigint unsigned     not null comment '商品id',
  product_stock   int unsigned        not null default 0 comment '库存量',
  extension_field varchar(255)        not null default '' comment '扩展字段（json格式）',
  version         int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create      datetime            not null default current_timestamp comment '创建时间',
  gmt_modified    datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted      tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (inventory_id)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '库存表';
-- -- fescar undo log
drop table if exists `undo_log`;
create table `undo_log`
(
  `id`            bigint(20)   not null auto_increment,
  `branch_id`     bigint(20)   not null,
  `xid`           varchar(100) not null,
  `rollback_info` longblob     not null,
  `log_status`    int(11)      not null,
  `log_created`   datetime     not null,
  `log_modified`  datetime     not null,
  `ext`           varchar(100) default null,
  primary key (`id`),
  unique key `ux_undo_log` (`xid`, `branch_id`)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment 'fescar_undo_log';

-- 云存储域名库
drop database if exists sun8min_cloud_domain;
create database sun8min_cloud_domain default charset utf8 collate utf8_unicode_ci;
use sun8min_cloud_domain;
-- -- 云存储域名表
drop table if exists sun8min_cloud_domain;
create table sun8min_cloud_domain
(
  cloud_domain_id     bigint unsigned auto_increment comment '云存储域名id',
  cloud_domain_name   varchar(255)        not null default '' comment '域名',
  cloud_domain_target tinyint unsigned    not null default 0 comment '针对目标标识（0：未知，1：所有，2：商品，3：品牌）',
  cloud_domain_type   tinyint unsigned    not null default 0 comment '类型标识（0：未知，1:所有，2：图片，3：视频）',
  cloud_domain_desc   varchar(255)        not null default '' comment '描述',
  extension_field     varchar(255)        not null default '' comment '扩展字段（json格式）',
  version             int unsigned        not null default 0 comment '版本号（用于乐观锁）',
  gmt_create          datetime            not null default current_timestamp comment '创建时间',
  gmt_modified        datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
  is_deleted          tinyint(1) unsigned not null default 0 comment '是否删除（0：否，1：是）',
  primary key (cloud_domain_id)
) engine = innodb
  default charset = utf8
  collate = utf8_unicode_ci
  auto_increment = 1 comment '云存储域名表';

-- ###################################
-- 基础数据插入

-- 用户库
use sun8min_user;
-- -- 用户表
insert into sun8min_user(user_nick_name)
values ('aaa'),
       ('bbb'),
       ('ccc');

-- 账户库
use sun8min_account;
-- -- 账户表
insert into sun8min_account(user_id, account_amount)
values (1, 100000),
       (2, 100000),
       (3, 100000);

-- 商店库
use sun8min_product;
-- 商店表
insert into sun8min_shop(shop_name, user_id)
values ('aaa的小店', 1),
       ('bbb的小店', 2);
-- -- 商品表
insert into sun8min_product(product_name, product_price, shop_id)
values ('a1', 1, 1),
       ('a2', 10, 1),
       ('a3', 100, 1),
       ('a4', 1000, 1),
       ('a5', 10000, 1),
       ('b1', 1, 2),
       ('b2', 10, 2),
       ('b3', 100, 2),
       ('b4', 1000, 2),
       ('b5', 10000, 2);

-- 云存储域名库
use sun8min_cloud_domain;
-- -- 云存储域名表
insert into sun8min_cloud_domain(cloud_domain_name, cloud_domain_target, cloud_domain_type, cloud_domain_desc)
values ('http://img.sun8min.com', 1, 2, '阿里云oss，用于存储图片'),
       ('http://vod.sun8min.com', 1, 3, '阿里云视频点播，用于存储视频');

-- ###################################
-- 测试数据插入

-- 商品库
use sun8min_product;
-- -- 类目表
insert into sun8min_category(category_name, category_pid, category_rid, category_level, category_type, category_sort)
values ("前台test", 0, 0, 0, 1, 0),
       ("后台test", 0, 0, 0, 2, 0);