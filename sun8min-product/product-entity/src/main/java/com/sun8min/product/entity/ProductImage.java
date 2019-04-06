package com.sun8min.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品图片表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_product_image")
public class ProductImage extends Model<ProductImage> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品图片id
     */
    @TableId(value = "product_image_id", type = IdType.AUTO)
    private BigInteger productImageId;

    /**
     * 商品图片名
     */
    @TableField("product_image_name")
    private String productImageName;

    /**
     * 商品id
     */
    @TableField("product_id")
    private BigInteger productId;

    /**
     * 商品图片存储（不含域名）
     */
    @TableField("product_image")
    private String productImage;

    /**
     * 排序id（默认应该和主键相同,调整排序好修改）
     */
    @TableField("product_image_sort")
    private BigInteger productImageSort;

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
        return this.productImageId;
    }

}
