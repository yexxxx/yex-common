package me.yex.cloudfeign.config;

import feign.Contract;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.config
 */

@Configuration
public class CloudFeignConfiguration {
    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    Decoder feignDecoder() {
        return new JacksonDecoder();
    }

    @Bean
    Encoder feignEncoder() {
        return new JacksonEncoder();
    }
}
