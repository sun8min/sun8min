package com.sun8min.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * <p>
 * 属性key表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_property_key")
public class PropertyKey extends Model<PropertyKey> {

    private static final long serialVersionUID = 1L;

    /**
     * 属性key_id
     */
    @TableId(value = "property_key_id", type = IdType.AUTO)
    private BigInteger propertyKeyId;

    /**
     * 属性key名
     */
    @TableField("property_key_name")
    private String propertyKeyName;

    /**
     * 属性key类型（0：未知，1：关键属性，2：非关键属性，3：销售属性，ps：影响具体商品价格、库存的即销售属性，例如：手机容量，而关键属性即能确定spu的属性，例如：手机型号，其他即非关键属性，例如：手机毛重）
     */
    @TableField("property_key_type")
    private Integer propertyKeyType;

    /**
     * 排序id（默认应该和主键相同,调整排序好修改）
     */
    @TableField("property_key_sort")
    private BigInteger propertyKeySort;

    /**
     * 是否必填（0：否，1：是）
     */
    @TableField("is_required")
    private Boolean required;

    /**
     * 类目id
     */
    @TableField("category_id")
    private BigInteger categoryId;

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
        return this.propertyKeyId;
    }

    /**
     * 属性key类型枚举类（0：未知，1：关键属性，2：非关键属性，3：销售属性，ps：影响具体商品价格、库存的即销售属性，例如：手机容量，而关键属性即能确定spu的属性，例如：手机型号，其他即非关键属性，例如：手机毛重）
     */
    @Getter
    @AllArgsConstructor
    public enum PropertyKeyType {
        UNKNOWN(0, "未知"),
        IMPORTANT(1, "关键属性"),
        UN_IMPORTANT(2, "非关键属性"),
        SALE(3, "销售属性");

        private int value;
        private String desc;
    }

}
