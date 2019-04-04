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
 * 商品上架定时表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_product_schedule")
public class ProductSchedule extends Model<ProductSchedule> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品商品关联id
     */
    @TableId(value = "product_schedule_id", type = IdType.AUTO)
    private BigInteger productScheduleId;

    /**
     * 定时任务类型（0：一次性[使用上下架时间]，1：周期性[使用cron表达式]）
     */
    @TableField("schedule_type")
    private Integer scheduleType;

    /**
     * 上架时间
     */
    @TableField("gmt_up_shelves")
    private LocalDateTime gmtUpShelves;

    /**
     * 下架时间
     */
    @TableField("gmt_down_shelves")
    private LocalDateTime gmtDownShelves;

    /**
     * cron表达式
     */
    @TableField("cron_expression")
    private String cronExpression;

    /**
     * corn执行开始时间
     */
    @TableField("cron_gmt_begin")
    private LocalDateTime cronGmtBegin;

    /**
     * corn执行结束时间
     */
    @TableField("cron_gmt_end")
    private LocalDateTime cronGmtEnd;

    /**
     * cron周期执行时，商品上架持续时间（单位：秒）
     */
    @TableField("cron_up_shelves_duration")
    private BigInteger cronUpShelvesDuration;

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
        return this.productScheduleId;
    }

    /**
     * 定时任务类型枚举类（0：一次性[使用上下架时间]，1：周期性[使用cron表达式]）
     */
    @Getter
    @AllArgsConstructor
    public enum ScheduleType {
        ONETIME(0, "一次性[使用上下架时间]"),
        CRON(1, "周期性[使用cron表达式]");

        private int value;
        private String desc;
    }

}
