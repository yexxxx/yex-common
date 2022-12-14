package me.yex.common.test.feign.payment.entity;

import lombok.Data;

/**
 * @author yex
 * @description cn.zhenhealth.health.pay.vo.response
 */
@Data
public class PrepayResultVO {
    //支付单id
    private long payId;

    //wx预支付id
    private String prepayId;

    private long timeStamp;

    //随机字符串
    private String nonceStr;

    private String signType = "HMAC-SHA256";

    private String paySign;
}
