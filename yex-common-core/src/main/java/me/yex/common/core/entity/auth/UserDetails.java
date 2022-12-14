package me.yex.common.core.entity.auth;

import java.util.Collection;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.entity
 */
public interface UserDetails {

    long getLoginId();

    long getUserId();

    String getAccount();

    String getAccountType();

    String getClient();

    String getPlatform();

    TokenInfo getTokenInfo();

    default Collection<String> getAuthInfos() {
        return null;
    }

    default Collection<String> getRoleInfos() {
        return null;
    }

    default Collection<Integer> getOwnOrgCollection() {
        return null;
    }
}
