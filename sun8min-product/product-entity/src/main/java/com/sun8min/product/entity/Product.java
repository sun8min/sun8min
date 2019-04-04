package com.sun8min.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_product")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "product_id", type = IdType.AUTO)
    private BigInteger productId;

    /**
     * 商品名
     */
    @TableField("product_name")
    private String productName;

    /**
     * sku_id
     */
    @TableField("sku_id")
    private BigInteger skuId;

    /**
     * 计量单位（0：个，1：ml）
     */
    @TableField("product_measure_unit")
    private Integer productMeasureUnit;

    /**
     * 商品数量
     */
    @TableField("product_quantity")
    private Long productQuantity;

    /**
     * 商品视频存储（不含域名）
     */
    @TableField("product_video")
    private String productVideo;

    /**
     * 商品售价（精确到分）
     */
    @TableField("product_price")
    private BigDecimal productPrice;

    /**
     * 商品折扣类型（0：无折扣，1：输入折后价，2：输入折扣百分比）
     */
    @TableField("product_discount_type")
    private Integer productDiscountType;

    /**
     * 商品折后价（精确到分）
     */
    @TableField("product_discount_price")
    private BigDecimal productDiscountPrice;

    /**
     * 商品折扣百分比
     */
    @TableField("product_discount_percent")
    private Integer productDiscountPercent;

    /**
     * 商品类型（0：单品，1：打包品，ps：非单品即需要去关联表找子项）
     */
    @TableField("product_type")
    private Integer productType;

    /**
     * 是否上架（0：否，1：是）
     */
    @TableField("is_up_shelves")
    private Boolean upShelves;

    /**
     * 是否展示（0：否，1：是）ps:保证可售卖而用户不可直接购买该单品，用例如：打包品中的单品，只能通过打包品买
     */
    @TableField("is_visible")
    private Boolean visible;

    /**
     * 所属商店id
     */
    @TableField("shop_id")
    private BigInteger shopId;

    /**
     * 商品活动标识（二进制位，为1表示是，右侧第1为1，1：秒杀，2：拼团，3：预售，4：团购，5：拍卖，ps:拼团是基于熟人之间的社交化电商传播，团购则是陌生人之间的传播）
     */
    @TableField("product_activity_flag")
    private Long productActivityFlag;

    /**
     * 扩展字段（json格式）
     */
    @TableField("extension_field")
    private String extensionField;

    /**
     * 版本号（用于乐观锁）
     */
    @TableField("version")
    @Version
    private Long version;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;

    /**
     * 是否删除（0：否，1：是）
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


    @Override
    protected Serializable pkVal() {
        return this.productId;
    }

    /**
     * 计量单位枚举类（0：个，1：ml）
     */
    @Getter
    @AllArgsConstructor
    public enum ProductMeasureUnit {
        UNIT(0, "个"),
        ML(1, "ml");

        private int value;
        private String desc;
    }

    /**
     * 商品折扣类型枚举类（0：无折扣，1：输入折后价，2：输入折扣百分比）
     */
    @Getter
    @AllArgsConstructor
    public enum ProductDiscountType {
        EMPTY(0, "无折扣"),
        PRICE(1, "输入折后价"),
        PERCENT(2, "输入折扣百分比");

        private int value;
        private String desc;
    }

    /**
     * 商品类型枚举类（0：单品，1：打包品，ps：非单品即需要去关联表找子项）
     */
    @Getter
    @AllArgsConstructor
    public enum ProductType {
        DRAFT(0, "单品"),
        PAYING(1, "打包品");

        private int value;
        private String desc;
    }

    /**
     * 商品活动标识枚举类（二进制位，为1表示是，右侧第1为1，1：秒杀，2：拼团，3：预售，4：团购，5：拍卖，ps:拼团是基于熟人之间的社交化电商传播，团购则是陌生人之间的传播）
     */
    @Getter
    @AllArgsConstructor
    public enum ProductActivityFlag {
        SECKILL(1, "秒杀"),
        GROUP_ACQUAINTANCE(2, "拼团"),
        PRESALE(3, "预售"),
        GROUP_STRANGER(4, "团购"),
        AUCTION(5, "拍卖");

        private int value;
        private String desc;
    }

}
