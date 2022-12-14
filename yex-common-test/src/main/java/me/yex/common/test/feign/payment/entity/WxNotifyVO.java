package me.yex.common.test.feign.payment.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author yex
 * @description cn.zhenhealth.health.pay.feign.transaction.entity
 */

@Data
public class WxNotifyVO {
    private long orderNo;

    private String orderStatus;

    //微信支付回调 支付完成时间
    private Date payTime;
}
