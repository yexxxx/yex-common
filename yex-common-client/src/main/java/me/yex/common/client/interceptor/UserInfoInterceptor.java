package me.yex.common.client.interceptor;

import me.yex.common.client.constant.ResultEnum;
import me.yex.common.core.constant.AuthConstant;
import me.yex.common.core.entity.auth.UserInfo;
import me.yex.common.core.exception.GlobalException;
import me.yex.common.core.oauth.SessionContext;
import me.yex.common.core.oauth.SessionContextHolder;
import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.interceptor
 *              将请求中user信息写入sessionContext
 */

//@Component
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = false;

        //先判断userInfo header
        String userInfoStr = request.getHeader(AuthConstant.TokenConstant.USER_INFO);

        UserInfo userInfo = JSON.parseObject(userInfoStr,UserInfo.class);

        if (userInfo != null){
            result = true;
            SessionContextHolder.setContext(new SessionContext().setUserInfo(userInfo));
        }

        if (!result) {
            throw new GlobalException(ResultEnum.TOKEN_INVALID);
        }

        return result;
    }
}
