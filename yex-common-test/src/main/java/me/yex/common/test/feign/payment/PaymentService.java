package me.yex.common.test.feign.payment;

import me.yex.common.core.entity.R;
import me.yex.common.test.feign.member.entity.Membership;
import me.yex.common.test.feign.payment.entity.PrepayResultVO;
import me.yex.common.test.feign.payment.entity.PrepayVo;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * @author yex
 * @description cn.zhenhealth.health.transaction.feign
 */
public interface PaymentService {

    //    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("GET /membership/{id}")
    R<Membership> getMembershipById(@Param("id") Integer id);

    //预支付
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("POST /pay/prepay")
//    @Body("{prepayVo}")
    R<PrepayResultVO> prepay(PrepayVo prepayVo);

    //预支付
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("GET /demo/hello?name={name}")
//    @Body("{prepayVo}")
    R<String> demoHello(@Param("name") String name);

}
