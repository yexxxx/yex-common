package me.yex.common.core.entity.auth;

import lombok.Data;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.entity.auth
 */

@Data
public class TokenInfo {

    private String accessToken;

    private String tokenType;

    private String refreshToken;

    private long expireTime;
}
