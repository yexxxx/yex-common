package me.yex.common.core.constant;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.constant
 */
public interface AuthConstant {
    interface TokenConstant {
        String HEADER = "Authorization";
        String PREFIX = "Bearer ";

        String ACCESS_AUTH = "access_auth";
        String REFRESH_AUTH = "refresh_auth";
        String ACCESS = "access";
        String REFRESH = "refresh";
        String ACCESS_TO_REFRESH = "access_to_refresh";
        String REFRESH_TO_ACCESS = "refresh_to_access";

        String USER_INFO = "userInfo";
    }

    interface TokenType {
        String BEARER = "Bearer";
    }

    interface FeignConstant {
        String VALID_HEADER = "validFeign";
        String VALID_HEADER_VALUE = "been checked";
    }

    interface PermissionConstant {
        String AND = "and";
        String OR = "or";
    }

    interface ValidateImageConstant{
        /**
         * gif类型
         */
        public static final String GIF = "gif";
        /**
         * png类型
         */
        public static final String PNG = "png";

        /**
         * 验证码 key前缀
         */
        public static final String CODE_PREFIX = "zn.captcha.";
    }

}
