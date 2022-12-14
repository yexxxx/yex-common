package me.yex.common.test.feign.member;

import me.yex.common.core.entity.R;
import me.yex.common.test.feign.member.entity.Account;
import me.yex.common.test.feign.member.entity.Membership;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * @author yex
 * @description cn.zhenhealth.health.transaction.feign
 */
public interface MemberService {

//    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("GET /account/{id}")
    R<Account> getById(@Param("id") Integer id);

//    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("POST /account/id")
    R<Account> getOne(@Param("id") Integer id);


    //    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("GET /membership/{id}")
    R<Membership> getMembershipById(@Param("id") Integer id);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("POST /membership/post/id")
    @Body("{id}")
    R<Membership> getOneMembership(@Param("id") Integer id);


}
