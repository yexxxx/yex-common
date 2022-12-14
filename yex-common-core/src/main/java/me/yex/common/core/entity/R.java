package me.yex.common.core.entity;

import me.yex.common.core.constant.enums.BaseResultEnum;
import me.yex.common.core.constant.enums.ResultEnumInterface;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author yex
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @JsonView(RView.class)
    @Schema(description = "状态码")
    private int code;

    @Getter
    @Setter
    @JsonView(RView.class)
    @Schema(description = "返回信息")
    private String message;

    @Getter
    @Setter
    @JsonView(RView.class)
    @Schema(description = "返回数据")
    private T data;

    public static <T> R<T> result(ResultEnumInterface resultEnum){
        return restResult(null,resultEnum.getCode(),resultEnum.getMessage());
    }

    public static <T> R<T> result(ResultEnumInterface resultEnum, T data){
        return restResult(data,resultEnum.getCode(),resultEnum.getMessage());
    }

    public static <T> R<T> result(int code, String msg) {
        return restResult(null,code,msg);
    }

    public static <T> R<T> ok() {
        return restResult(BaseResultEnum.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(BaseResultEnum.SUCCESS, data);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, BaseResultEnum.SUCCESS.getCode(), msg);
    }

    public static <T> R<T> failed() {
        return restResult(BaseResultEnum.INTERNAL_SERVER_ERROR,null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, BaseResultEnum.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(BaseResultEnum.INTERNAL_SERVER_ERROR,null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, BaseResultEnum.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        return new R<T>().setCode(code).setData(data).setMessage(msg);
    }

    private static <T> R<T> restResult(BaseResultEnum baseResultEnum, T data){
        return new R<T>().setCode(baseResultEnum.getCode()).setMessage(baseResultEnum.getMessage()).setData(data);
    }
    public interface RView{}
}
