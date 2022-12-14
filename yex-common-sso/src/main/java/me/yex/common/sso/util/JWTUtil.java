package me.yex.common.sso.util;

import me.yex.common.core.entity.auth.UserInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;


import java.util.Date;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.util
 */
@Slf4j
public class JWTUtil {

    // 过期时间，这里设为5分钟
    private static final long EXPIRE_TIME = 5 * 60 * 1000;
    // 密钥
    private static final String SECRET = "ZhenHealthZZ";

    /**
     * 生成签名
     *
     * @param userInfo 登录信息
     * @return 加密后的token
     */
    public static String create(UserInfo userInfo) {
        try {
            Date now = new Date();
            Date expire = new Date(now.getTime() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET); //使用HS256算法
            return JWT.create() //创建令牌实例
//                    .withIssuedAt(now)  //签发时间
//                    .withExpiresAt(now) //过期时间
                    .withClaim("account", userInfo.getAccount())
                    .withClaim("accountType", userInfo.getAccountType())
                    .sign(algorithm);
        } catch (IllegalArgumentException | JWTCreationException e) {
            System.out.println(e.getClass() + e.getMessage());
        }
        return null;
    }

    /**
     * @param userInfo 登录信息
     * @param expire 过期时间 ms
     * @return 加密后的token
     */
    public static String create(UserInfo userInfo, long expire) {
        try {
            Date now = new Date();
            Date expireTime = new Date(now.getTime() + expire);
            Algorithm algorithm = Algorithm.HMAC256(SECRET); //使用HS256算法
            return JWT.create() //创建令牌实例
//                    .withIssuedAt(now)  //签发时间
                    .withExpiresAt(expireTime) //过期时间
                    .withClaim("account", userInfo.getAccount())
                    .withClaim("accountType", userInfo.getAccountType())
                    .sign(algorithm);
        } catch (IllegalArgumentException | JWTCreationException e) {
            System.out.println(e.getClass() + e.getMessage());
        }
        return null;
    }

    /**
     * 生成签名
     *
     * @param userInfo 登录信息
     * @param type token类型
     * @return 加密后的token
     */
    public static String create(UserInfo userInfo, Date createTime, String type) {
        try {
            Date now = new Date();
            Date expire = new Date(now.getTime() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET); //使用HS256算法
            return JWT.create() //创建令牌实例
                    .withIssuedAt(createTime)
                    .withClaim("type",type)
                    .withClaim("account", userInfo.getAccount())
                    .withClaim("accountType", userInfo.getAccountType())
                    .sign(algorithm);
        } catch (IllegalArgumentException | JWTCreationException e) {
            System.out.println(e.getClass() + e.getMessage());
        }
        return null;
    }

    /**
     * 校验token是否正确
     *
     * @param userInfo 登录信息
     * @param token     令牌
     * @return 是否正确
     */
    public static boolean verify(UserInfo userInfo, String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("account", userInfo.getAccount())
                    .withClaim("accountType", userInfo.getAccountType())
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (IllegalArgumentException | JWTVerificationException e) {
            log.info(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {

        UserInfo userInfo = new UserInfo();
        userInfo.setAccount("11123");
        userInfo.setAccountType("wechat");

        String sign1 = create(userInfo,10 * 1000);
        String sign2 = create(userInfo,100 * 1000);

        System.out.println(sign1);
        System.out.println(sign2);
        System.out.println(sign1.equals(sign2));

        boolean verify = verify(userInfo, sign1);
        boolean verify2 = verify(userInfo, sign2);
        System.out.println(verify);
        System.out.println(verify2);
    }
}
