package com.sun8min.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品快照表（记录价格、活动、上下架、删除变动）
 * ps: 枚举内容去product类获取
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_product_snapshot")
public class ProductSnapshot extends Model<ProductSnapshot> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品快照id
     */
    @TableId(value = "product_snapshot_id", type = IdType.AUTO)
    private BigInteger productSnapshotId;

    /**
     * 商品id
     */
    @TableField("product_id")
    private BigInteger productId;

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
     * 商品活动标识（二进制位，为1表示是，右侧第1为1，1：秒杀，2：拼团，3：预售，4：团购，5：拍卖，ps:拼团是基于熟人之间的社交化电商传播，团购则是陌生人之间的传播）
     */
    @TableField("product_activity_flag")
    private Long productActivityFlag;

    /**
     * 商品是否删除（0：否，1：是）
     */
    @TableField("product_is_deleted")
    private Boolean productIsDeleted;

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
        return this.productSnapshotId;
    }

}
