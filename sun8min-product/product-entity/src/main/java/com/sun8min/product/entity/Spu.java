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
 * spu表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_spu")
public class Spu extends Model<Spu> {

    private static final long serialVersionUID = 1L;

    /**
     * spu_id
     */
    @TableId(value = "spu_id", type = IdType.AUTO)
    private BigInteger spuId;

    /**
     * spu名
     */
    @TableField("spu_name")
    private String spuName;

    /**
     * spu图片存储（不含域名）
     */
    @TableField("spu_image")
    private String spuImage;

    /**
     * spu视频存储（不含域名）
     */
    @TableField("spu_video")
    private String spuVideo;

    /**
     * 品牌id
     */
    @TableField("brand_id")
    private BigInteger brandId;

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
    private Integer version;

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
        return this.spuId;
    }

}
