package me.yex.common.client.interceptor;

import cn.hutool.core.util.ObjectUtil;
import me.yex.common.client.constant.ResultEnum;
import me.yex.common.client.util.TokenUtil;
import me.yex.common.core.constant.AuthConstant;
import me.yex.common.core.entity.auth.UserInfo;
import me.yex.common.core.exception.GlobalException;
import me.yex.common.core.oauth.SessionContext;
import me.yex.common.core.oauth.SessionContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.interceptor
 */

//@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    TokenUtil tokenUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = false;

        //先判断feign header
        String feignHeader = request.getHeader(AuthConstant.FeignConstant.VALID_HEADER);
        if (ObjectUtil.isNotEmpty(feignHeader)){
            if(AuthConstant.FeignConstant.VALID_HEADER_VALUE.equals(feignHeader))
            {
                result = true;
            }
        } else {//判断token
            //获取请求头（如果有此请求头，表示token已经签发）
            String tokenHeader = request.getHeader(AuthConstant.TokenConstant.HEADER);
            if (ObjectUtil.isNotEmpty(tokenHeader)) {

                //解析请求头（防止伪造token，token内容以"Bearer "作为开头）
                if (tokenHeader.startsWith(AuthConstant.TokenConstant.PREFIX)) {
                    String accessToken = tokenHeader.substring(AuthConstant.TokenConstant.PREFIX.length());
                    UserInfo userInfo = tokenUtil.validateAccessToken(accessToken);
                    if (userInfo != null) {
                        result = true;
                        SessionContextHolder.setContext(new SessionContext().setUserInfo(userInfo));
                    }
                }
            }
        }


        if (!result) {
            throw new GlobalException(ResultEnum.TOKEN_INVALID);
        }
        //所有请求都通过，具体权限在service层判断
        return result;
    }
}
