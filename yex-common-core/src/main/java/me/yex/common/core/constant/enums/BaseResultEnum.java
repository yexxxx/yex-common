package me.yex.common.core.constant.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author yex
 * @description cn.zhenhealth.health.common.core.entity
 */
public enum BaseResultEnum implements ResultEnumInterface {
    SUCCESS(200, "成功!"),
    BODY_NOT_MATCH(400,"请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(401,"请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503,"服务器正忙，请稍后再试!"),
    VALUE_NOT_IN_ENUM(8000,"必须为指定值{}");

    /** 错误码 */
    @Getter
    private int code;

    /** 错误描述 */
    @Getter
    private String message;

    BaseResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    BaseResultEnum(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }
}
