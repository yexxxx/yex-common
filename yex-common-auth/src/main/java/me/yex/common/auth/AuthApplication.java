package me.yex.common.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yexy
 * @date 2021/3/18 7:16 下午
 * @description
 */

//@EnableOauth2FeignClient   //自定义feign 权限注解
//@EnableAuthExceptionHandler
//@EnableServerProtect
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
    }
}
