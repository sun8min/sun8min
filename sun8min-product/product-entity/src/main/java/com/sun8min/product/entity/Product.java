package com.sun8min.product.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.math.BigInteger;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "product_id", type = IdType.AUTO)
    private BigInteger productId;

    /**
     * 商品名
     */
    private String productName;

    /**
     * sku_id
     */
    private BigInteger skuId;

    /**
     * 计量单位（0：个，1：ml）
     */
    private Integer productMeasureUnit;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    /**
     * 商品视频存储（不含域名）
     */
    private String productVideo;

    /**
     * 商品售价（精确到分）
     */
    private BigDecimal productPrice;

    /**
     * 商品折扣类型（0：无折扣，1：输入折后价，2：输入折扣百分比）
     */
    private Integer productDiscountType;

    /**
     * 商品折后价（精确到分）
     */
    private BigDecimal productDiscountPrice;

    /**
     * 商品折扣百分比
     */
    private Integer productDiscountPercent;

    /**
     * 商品类型（0：单品，1：打包品，ps：非单品即需要去关联表找子项）
     */
    private Integer productType;

    /**
     * 是否上架（0：否，1：是）
     */
    private Boolean upShelves;

    /**
     * 是否展示（0：否，1：是）ps:保证可售卖而用户不可直接购买该单品，用例如：打包品中的单品，只能通过打包品买
     */
    private Boolean show;

    /**
     * 所属商店id
     */
    private BigInteger shopId;

    /**
     * 商品活动标识（二进制位，为1表示是，右侧第1为1，1：秒杀，2：拼团，3：预售，4：团购，5：拍卖）
     */
    private Integer productActivityFlag;

    /**
     * 扩展字段（json格式）
     */
    private String extensionField;

    /**
     * 版本号（用于乐观锁）
     */
    private Integer version;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 是否删除（0：否，1：是）
     */
    private Boolean deleted;


}
