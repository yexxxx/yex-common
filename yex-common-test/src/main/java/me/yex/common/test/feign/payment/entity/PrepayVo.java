package me.yex.common.test.feign.payment.entity;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author yex
 * @description cn.zhenhealth.health.pay.vo
 */

@Data
public class PrepayVo {

    private int userId;

    private String openId;

    private long orderNo;

    private long amount;

    //string[1,127]
    private String productDescription;

    private String expireTime;


    public static void main(String[] args) {

//        DatePattern
        System.out.println(DateUtil.format(new Date(), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")));
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        System.out.println(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        System.out.println(DateUtil.format(new Date(),DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

}
