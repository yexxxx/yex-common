package me.yex.common.sso.util;

import me.yex.common.core.constant.AuthConstant;
import me.yex.common.core.entity.auth.TokenInfo;
import me.yex.common.core.entity.auth.UserInfo;
import me.yex.common.core.exception.GlobalException;
import me.yex.common.core.util.JWTUtil;
import me.yex.common.redis.util.RedisUtil;
import me.yex.common.sso.constant.ResultEnum;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.util
 */

@Data
@Component
@RequiredArgsConstructor
public class TokenUtil {

    private final RedisUtil redisUtil;

    private final long TOKEN_EXPIRE = 2 * 60 * 60;

    private final long REFRESH_EXPIRE = 3 * TOKEN_EXPIRE;

    public String getAccessTokenKey(HttpServletRequest request, String tokenType) {
        String authorization = request.getHeader("Authorization");
        return authorization.substring(tokenType.length());
    }

    public String getAccessTokenKey(UserInfo userInfo, Date lastLoginTime) {
        return String.format("%s:%s", AuthConstant.TokenConstant.ACCESS, JWTUtil.create(userInfo, lastLoginTime, AuthConstant.TokenConstant.ACCESS));
    }

    public String getAccessAuthKey(UserInfo userInfo, Date lastLoginTime) {
        return String.format("%s:%s", AuthConstant.TokenConstant.ACCESS_AUTH, JWTUtil.create(userInfo, lastLoginTime, AuthConstant.TokenConstant.ACCESS));
    }

    public String getRefreshAuthKey(UserInfo userInfo, Date lastLoginTime) {
        return String.format("%s:%s", AuthConstant.TokenConstant.REFRESH_AUTH, JWTUtil.create(userInfo, lastLoginTime, AuthConstant.TokenConstant.ACCESS));
    }

    public String getAccessToRefreshTokenKey(UserInfo userInfo, Date lastLoginTime) {
        return String.format("%s:%s", AuthConstant.TokenConstant.ACCESS_TO_REFRESH, JWTUtil.create(userInfo, lastLoginTime, AuthConstant.TokenConstant.ACCESS));
    }

    public String getRefreshToAccessTokenKey(UserInfo userInfo, Date lastLoginTime) {
        return String.format("%s:%s", AuthConstant.TokenConstant.REFRESH_TO_ACCESS, JWTUtil.create(userInfo, lastLoginTime, AuthConstant.TokenConstant.REFRESH));
    }


    public TokenInfo createToken(UserInfo userInfo, Date createTime) {

        //根据用户登录信息生成token
        String accessToken = JWTUtil.create(userInfo, createTime, AuthConstant.TokenConstant.ACCESS);
        String refreshToken = JWTUtil.create(userInfo, createTime, AuthConstant.TokenConstant.REFRESH);

        //redis key
        String accessTokenKey = String.format("%s:%s", AuthConstant.TokenConstant.ACCESS, accessToken);
        String accessAuthKey = String.format("%s:%s", AuthConstant.TokenConstant.ACCESS_AUTH, accessToken);
        String refreshAuthKey = String.format("%s:%s", AuthConstant.TokenConstant.REFRESH_AUTH, refreshToken);
        String accessToRefreshTokenKey = String.format("%s:%s", AuthConstant.TokenConstant.ACCESS_TO_REFRESH, accessToken);
        String refreshToAccessTokenKey = String.format("%s:%s", AuthConstant.TokenConstant.REFRESH_TO_ACCESS, refreshToken);

        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setAccessToken(accessToken);
        tokenInfo.setRefreshToken(refreshToken);
        tokenInfo.setExpireTime(TOKEN_EXPIRE);
        tokenInfo.setTokenType(AuthConstant.TokenType.BEARER);
        userInfo.setTokenInfo(tokenInfo);

        //存redis
        redisUtil.set(accessTokenKey, accessToken, TOKEN_EXPIRE);
        redisUtil.set(accessAuthKey, userInfo, TOKEN_EXPIRE);
        redisUtil.set(refreshToAccessTokenKey, accessToken, REFRESH_EXPIRE);
        redisUtil.set(accessToRefreshTokenKey, refreshToken, REFRESH_EXPIRE);
        redisUtil.set(refreshAuthKey, userInfo, REFRESH_EXPIRE);

        return tokenInfo;
    }

    public void removeToken(UserInfo userInfo, Date lastLoginTime) {
        //根据用户登录信息生成token
        String accessToken = JWTUtil.create(userInfo, lastLoginTime, AuthConstant.TokenConstant.ACCESS);
        String refreshToken = JWTUtil.create(userInfo, lastLoginTime, AuthConstant.TokenConstant.REFRESH);

        //redis key
        //redis key
        String accessTokenKey = String.format("%s:%s", AuthConstant.TokenConstant.ACCESS, accessToken);
        String accessAuthKey = String.format("%s:%s", AuthConstant.TokenConstant.ACCESS_AUTH, accessToken);
        String refreshAuthKey = String.format("%s:%s", AuthConstant.TokenConstant.REFRESH_AUTH, refreshToken);
        String accessToRefreshTokenKey = String.format("%s:%s", AuthConstant.TokenConstant.ACCESS_TO_REFRESH, accessToken);
        String refreshToAccessTokenKey = String.format("%s:%s", AuthConstant.TokenConstant.REFRESH_TO_ACCESS, refreshToken);

        redisUtil.del(accessTokenKey, accessAuthKey, refreshAuthKey, refreshToAccessTokenKey, accessToRefreshTokenKey);
    }

    public void removeToken(String accessToken) {
        String accessToRefreshTokenKey = String.format("%s:%s", AuthConstant.TokenConstant.ACCESS_TO_REFRESH, accessToken);
        String refreshToken = (String) redisUtil.get(accessToRefreshTokenKey);

        String accessTokenKey = String.format("%s:%s", AuthConstant.TokenConstant.ACCESS, accessToken);
        String accessAuthKey = String.format("%s:%s", AuthConstant.TokenConstant.ACCESS_AUTH, accessToken);
        String refreshAuthKey = String.format("%s:%s", AuthConstant.TokenConstant.REFRESH_AUTH, refreshToken);
        String refreshToAccessTokenKey = String.format("%s:%s", AuthConstant.TokenConstant.REFRESH_TO_ACCESS, refreshToken);

        redisUtil.del(accessTokenKey, accessAuthKey, refreshAuthKey, refreshToAccessTokenKey, accessToRefreshTokenKey);
    }

    public TokenInfo refreshToken(UserInfo userInfo, Date lastLoginTime, Date creatTime) {
        removeToken(userInfo, lastLoginTime);
        return createToken(userInfo, creatTime);
    }

    public UserInfo validateAccessToken(String accessToken) {
        String accessAuthKey = String.format("%s:%s", AuthConstant.TokenConstant.ACCESS_AUTH, accessToken);
        UserInfo userInfo = (UserInfo) redisUtil.get(accessAuthKey);
        String accessToRefreshTokenKey = String.format("%s:%s", AuthConstant.TokenConstant.ACCESS_TO_REFRESH, accessToken);
        String refreshToken = (String) redisUtil.get(accessToRefreshTokenKey);

        //刷新token不存在 token不合法
        if (refreshToken == null) {
            throw new GlobalException(ResultEnum.TOKEN_INVALID);
        } else {
            //access token失效
            if (userInfo == null) {
                throw new GlobalException(ResultEnum.TOKEN_IS_EXPIRED);
            } else {
                return userInfo;
            }
        }

    }
}
