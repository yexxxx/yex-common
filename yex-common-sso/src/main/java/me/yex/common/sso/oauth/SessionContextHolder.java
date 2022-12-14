package me.yex.common.sso.oauth;

import org.springframework.util.Assert;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.oauth
 */
public class SessionContextHolder {
    private static final ThreadLocal<SessionContext> contextHolder = new ThreadLocal<>();

    public static void clearContext() {
        contextHolder.remove();
    }

    public static SessionContext getContext() {
        SessionContext ctx = contextHolder.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }
        return ctx;
    }

    public static void setContext(SessionContext context) {
        Assert.notNull(context, "Only non-null SessionContext instances are permitted");
        contextHolder.set(context);
    }

    public static SessionContext createEmptyContext() {
        return new SessionContext();
    }
}
