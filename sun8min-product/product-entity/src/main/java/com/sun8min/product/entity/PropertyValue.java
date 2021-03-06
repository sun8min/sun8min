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
 * 属性value表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_property_value")
public class PropertyValue extends Model<PropertyValue> {

    private static final long serialVersionUID = 1L;

    /**
     * 属性value_id
     */
    @TableId(value = "property_value_id", type = IdType.AUTO)
    private BigInteger propertyValueId;

    /**
     * 属性value名
     */
    @TableField("property_value_name")
    private String propertyValueName;

    /**
     * 排序id（默认应该和主键相同,调整排序好修改）
     */
    @TableField("property_value_sort")
    private BigInteger propertyValueSort;

    /**
     * 属性key_id
     */
    @TableField("property_key_id")
    private BigInteger propertyKeyId;

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
        return this.propertyValueId;
    }

}
