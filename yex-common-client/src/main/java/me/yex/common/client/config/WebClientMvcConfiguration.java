package me.yex.common.client.config;

import me.yex.common.client.aspect.HasPermissionAspect;
import me.yex.common.client.interceptor.UserInfoInterceptor;
import me.yex.common.redis.config.YexRedisConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.config
 */

//@Configuration
@ConditionalOnClass(YexRedisConfiguration.class)
public class WebClientMvcConfiguration implements WebMvcConfigurer {

//    @Autowired
//    private TokenInterceptor tokenInterceptor;

    @Autowired
    private UserInfoInterceptor userInfoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> excludeSwaggerUrls = Stream.of(
                "/swagger-ui.html/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/**",
//                "/doc.html",
//                "doc.html/**",
                "/favicon.ico",
                "/webjars/**",
                "META-INF/resources/",
                "/swagger-resources/**"
        ).collect(Collectors.toList());

        List<String> excludeUrls = Stream.of(
                "/error",
                "/login/**",
                "/register/**"
        ).collect(Collectors.toList());
        excludeUrls.addAll(excludeSwaggerUrls);

        excludeUrls.addAll(excludeSwaggerUrls);

        registry.addInterceptor(userInfoInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls);
    }

    @Bean
    public HasPermissionAspect hasPermissionAspect() {
        return new HasPermissionAspect();
    }
}