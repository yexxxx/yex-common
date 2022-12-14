package me.yex.common.sso.oauth;

import me.yex.common.core.entity.auth.UserInfo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.oauth
 */
@Data
@Accessors(chain = true)
public class SessionContext {
    UserInfo userInfo;
}
