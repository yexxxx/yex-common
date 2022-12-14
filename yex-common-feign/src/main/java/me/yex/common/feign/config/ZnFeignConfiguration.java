package me.yex.common.feign.config;

import me.yex.common.feign.properties.ZnFeignConfigProperties;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author yex
 * @description cn.zhenhealth.health.common.es.config
 */

/**
 * 由于feign service是通过反射注入到容器中的，spring不知道有哪些bean，
 * 所以需要@DependsOn({"cn.zhenhealth.health.common.feign.config.ZnFeignConfiguration"})
 * 或@DependsOn({"znFeignConfiguration"})
 *
*/
//
@Configuration("ZnFeignConfiguration")
//@Configuration
@EnableConfigurationProperties(ZnFeignConfigProperties.class)
public class ZnFeignConfiguration implements ApplicationContextAware{

    @Autowired
    ZnFeignConfigProperties properties;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();


        for (ZnFeignConfigProperties.FeignService feignService : properties.getFeignServices()) {

            Object target = Feign.builder()
                    .decoder(new JacksonDecoder())
                    .encoder(new JacksonEncoder())
                    .options(new Request.Options(properties.getConnectTimeout(), TimeUnit.MILLISECONDS, properties.getReadTimeout(), TimeUnit.MILLISECONDS,
                            true))
                    .retryer(new Retryer.Default(5000, 5000, 3))
                    .target(feignService.getTarget(), feignService.getUrl());

            defaultListableBeanFactory.registerSingleton(target.getClass().getName(),target);
        }

    }

}
