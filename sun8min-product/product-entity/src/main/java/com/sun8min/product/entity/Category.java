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
 * 类目表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_category")
public class Category extends Model<Category> {

    private static final long serialVersionUID = 1L;

    /**
     * 类目id
     */
    @TableId(value = "category_id", type = IdType.AUTO)
    private BigInteger categoryId;

    /**
     * 类目名
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 父id
     */
    @TableField("category_pid")
    private BigInteger categoryPid;

    /**
     * 根节点id
     */
    @TableField("category_rid")
    private BigInteger categoryRid;

    /**
     * 是否叶子节点（0：否，1：是）
     */
    @TableField("is_leaf")
    private Boolean leaf;

    /**
     * 是否包含映射（0：否，1：是，ps：是即需要去关联表找子项）
     */
    @TableField("is_contain_mapping")
    private Boolean containMapping;

    /**
     * 类目层级（从0开始）
     */
    @TableField("category_level")
    private Integer categoryLevel;

    /**
     * 类目类型（1：后台类目，2：前台类目）
     */
    @TableField("category_type")
    private Integer categoryType;

    /**
     * 排序id（默认应该和主键相同,调整排序好修改）
     */
    @TableField("category_sort")
    private BigInteger categorySort;

    /**
     * 类目图片存储（不含域名）
     */
    @TableField("category_image")
    private String categoryImage;

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
        return this.categoryId;
    }

    /**
     * 类目类型枚举类（1：后台类目，2：前台类目）
     */
    @Getter
    @AllArgsConstructor
    public enum CategoryType {
        FRONT(1, "后台类目"),
        BACK(2, "前台类目");

        private int value;
        private String desc;
    }

}
