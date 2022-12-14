package me.yex.common.core.entity.auth;

import lombok.Data;

import java.util.Collection;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.entity.auth
 */
@Data
public class UserInfo implements UserDetails{

    private long loginId;

    private long userId;

    private String account;

    private String accountType;

    private String client;

    private String platform;

    private TokenInfo tokenInfo;

    private Collection<String> roleInfos;

    private Collection<String> authInfos;

    private Collection<Integer> ownOrgCollection;

}
