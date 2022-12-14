package me.yex.common.core.exception;

import me.yex.common.core.constant.enums.ResultEnumInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author yex
 * @description cn.zhenhealth.health.common.core.entity
 */

@NoArgsConstructor
public class GlobalException extends RuntimeException{

    @Getter
    @Setter
    private int code;

    private static final long serialVersionUID = 1L;

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(int code, String message) {
        super(message);
        this.code = code;
    }

    public GlobalException(HttpStatus httpStatus) {
        super(httpStatus.getReasonPhrase());
        this.code = httpStatus.value();
    }

    public GlobalException(ResultEnumInterface resultEnumInterface) {
        super(resultEnumInterface.getMessage());
        this.code = resultEnumInterface.getCode();
    }

    public GlobalException(Throwable cause) {
        super(cause);
    }

    public GlobalException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public GlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
