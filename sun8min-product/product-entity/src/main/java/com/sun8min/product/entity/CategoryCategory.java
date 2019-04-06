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
 * 类目类目关联表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_category_category")
public class CategoryCategory extends Model<CategoryCategory> {

    private static final long serialVersionUID = 1L;

    /**
     * 类目类目id
     */
    @TableId(value = "category_category_id", type = IdType.AUTO)
    private BigInteger categoryCategoryId;

    /**
     * 类目id
     */
    @TableField("parent_category_id")
    private BigInteger parentCategoryId;

    /**
     * 子类目id
     */
    @TableField("child_category_id")
    private BigInteger childCategoryId;

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
        return this.categoryCategoryId;
    }

}
