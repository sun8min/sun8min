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
 * sku-销售属性关联表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_sku_property")
public class SkuProperty extends Model<SkuProperty> {

    private static final long serialVersionUID = 1L;

    /**
     * sku-销售属性关联id
     */
    @TableId(value = "sku_property_id", type = IdType.AUTO)
    private BigInteger skuPropertyId;

    /**
     * sku_id
     */
    @TableField("sku_id")
    private BigInteger skuId;

    /**
     * 属性key值
     */
    @TableField("property_key_id")
    private BigInteger propertyKeyId;

    /**
     * 属性value值
     */
    @TableField("property_value_id")
    private BigInteger propertyValueId;

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
        return this.skuPropertyId;
    }

}
