package me.yex.common.sso.interceptor;

import me.yex.common.core.constant.AuthConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.interceptor
 */

@Configuration
public class FeignTokenInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        String token = request.getHeader(AuthConstant.TokenConstant.HEADER);
        requestTemplate.header(AuthConstant.FeignConstant.VALID_HEADER,AuthConstant.FeignConstant.VALID_HEADER_VALUE);
        requestTemplate.header(AuthConstant.TokenConstant.HEADER, token);
    }
}
