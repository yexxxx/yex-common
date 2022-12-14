package me.yex.common.feign.annotation;

import org.springframework.core.annotation.AliasFor;

/**
 * @author yex
 * @description cn.zhenhealth.health.common.feign.annotation
 */
public @interface ZnFeignService {
    String name() default "";

    Class<?> target() default Object.class;
}
