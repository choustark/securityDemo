package com.chou.securityDemo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 实体类基类
 *
 * @author by Axel
 * @since 2024/5/26 下午6:13
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 是否删除标识 1：是，0否
     */
    private Integer deleteFlag;

    /**
     *  创建人
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 插入数据库之前设置值
     * @param userId  用户id
     */
    public void insertBefore(Long userId) {
        Date now = new Date();
        this.setCreatedBy(userId);
        this.setCreatedTime(now);
        this.setUpdatedBy(userId);
        this.setUpdatedTime(now);
    }

    /**
     * 更新数据库之前设置值
     * @param userId  用户id
     */
    public void updateBefore(Long userId) {
        this.setUpdatedBy(userId);
        this.setUpdatedTime(new Date());
    }
}
