package me.yex.common.client.interceptor;

import me.yex.common.client.constant.ResultEnum;
import me.yex.common.core.constant.AuthConstant;
import me.yex.common.core.entity.auth.UserInfo;
import me.yex.common.core.exception.GlobalException;
import me.yex.common.core.oauth.SessionContext;
import me.yex.common.core.oauth.SessionContextHolder;
import com.alibaba.fastjson.JSON;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.interceptor
 *              将feign中user信息写入sessionContext
 */

//@Component
public class FeignUserInfoInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        String token = request.getHeader(AuthConstant.TokenConstant.HEADER);
        Map<String, Collection<String>> headers = requestTemplate.headers();
        String userInfoStr = headers.get(AuthConstant.TokenConstant.USER_INFO).stream().findFirst().get();

        try {
            UserInfo userInfo = JSON.parseObject(userInfoStr,UserInfo.class);
            if (userInfo != null){
                SessionContextHolder.setContext(new SessionContext().setUserInfo(userInfo));
            }
        }catch (Exception e){
            throw new GlobalException(ResultEnum.TOKEN_INVALID);
        }
    }
}
