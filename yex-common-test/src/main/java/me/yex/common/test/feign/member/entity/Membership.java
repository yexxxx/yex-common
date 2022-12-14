package me.yex.common.test.feign.member.entity;

import me.yex.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName membership
 */
@Data
public class Membership extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 全局唯一的用户ID，对应用户表中的唯一标识
     */
    private String userId;

    /**
     * 如果有会员等级体系，则需要此字段
     */
    private String level;

    /**
     * 会员权益描述, 用于页面展示
     */
    private String rights;

    /**
     * 会员权益到期时间
     */
    private LocalDateTime expireTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}