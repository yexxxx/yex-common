package me.yex.common.feign.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yex
 * @description me.yex.health.common.feign.properties
 */

@Data
@ConfigurationProperties(prefix = "zn.feign")
public class ZnFeignConfigProperties {

    private List<FeignService> feignServices = Collections.emptyList();

    //TimeUnit.MILLISECONDS
    private long connectTimeout = 1000;

    //TimeUnit.MILLISECONDS
    private long readTimeout = 3500;


    @Data
    public static class FeignService{
        private String name;
        private Class<?> target;
        private String url;
    }
}
