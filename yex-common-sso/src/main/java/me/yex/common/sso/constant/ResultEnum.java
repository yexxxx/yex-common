package me.yex.common.sso.constant;

import me.yex.common.core.constant.enums.ResultEnumInterface;
import lombok.Getter;

/**
 * @author yex
 * @description cn.zhenhealth.health.transaction.constant.enums
 */
public enum ResultEnum implements ResultEnumInterface {

    TOKEN_IS_EXPIRED(9000,"token过期"),
    USER_NOT_EXISTED(9001,"用户不存在"),
    REFRESH_TOKEN_INVALID(9002,"refresh token无效"),
    TOKEN_INVALID(9003,"token无效"),
    CHECK_ORDER_AMOUNT_FAILED(9999, "订单支付金额校验失败");
    /** 错误码 */
    @Getter
    private int code;

    /** 错误描述 */
    @Getter
    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
