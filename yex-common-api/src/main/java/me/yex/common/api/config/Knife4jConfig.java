/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package me.yex.common.api.config;

import cn.hutool.core.collection.CollectionUtil;
import me.yex.common.api.props.ApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.ApiSelector;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@EnableOpenApi
//@Configuration
@Profile({"dev", "test"})
@ConditionalOnProperty(name = "api.enable")
@EnableConfigurationProperties(ApiProperties.class)
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfig {

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        //1.获取context:ConfigurableApplicationContext.其中applicationContext 可以是注入的
        ConfigurableApplicationContext configFactory = applicationContext instanceof
                ConfigurableApplicationContext ? ((ConfigurableApplicationContext) applicationContext) : null;


        //2.获取factory:DefaultListableBeanFactory
        assert configFactory != null;
        ConfigurableListableBeanFactory beanFactory = configFactory.getBeanFactory();
        DefaultListableBeanFactory listFactory = beanFactory instanceof
                DefaultListableBeanFactory ? ((DefaultListableBeanFactory) beanFactory) : null;

        ApiInfo apiInfo = apiInfo();
        if (CollectionUtil.isNotEmpty(apiProperties.getGroups())) {
            for (ApiProperties.Group group : apiProperties.getGroups()) {
                //3.动态构建bean：BeanDefinitionBuilder
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(Docket.class)
                        .addConstructorArgValue(DocumentationType.OAS_30)
                        .addPropertyValue("apiInfo", apiInfo)
                        .addPropertyValue("groupName",group.getName())
                        .addPropertyValue("apiSelector",new ApiSelector(RequestHandlerSelectors.basePackage(group.getBasePackage()),PathSelectors.any()));

                AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
                listFactory.registerBeanDefinition(group.getName(), beanDefinition);
            }
        }


    }

    //    @Bean(value = "defaultApi1")
    public Docket defaultApi1() {
        //List<SecurityScheme> securitySchemes=Arrays.asList(new ApiKey("Authorization", "Authorization", "header"));
        List<SecurityScheme> securitySchemes = new ArrayList<>();

        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        List<SecurityContext> securityContexts = Arrays.asList(SecurityContext.builder()
                .securityReferences(CollectionUtil.newArrayList(new SecurityReference("Authorization", authorizationScopes)))
                .forPaths(PathSelectors.regex("/.*"))
                .build());
        HttpAuthenticationScheme httpAuthenticationScheme = HttpAuthenticationScheme.JWT_BEARER_BUILDER
                .name(HttpHeaders.AUTHORIZATION)
                .description("Bearer Token")
                .build();
        securitySchemes.add(httpAuthenticationScheme);

        //默认全局参数
        List<RequestParameter> requestParameters = new ArrayList<>();
        requestParameters.add(new RequestParameterBuilder().name("test").description("测试").in(ParameterType.QUERY).required(true).build());

        Docket docket = new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("2")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("cn.zhenhealth.health."))
                .paths(PathSelectors.any())
                .build()
                //.globalRequestParameters(requestParameters)
                .securityContexts(securityContexts).securitySchemes(securitySchemes);
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(apiProperties.getTitle())
                .description(apiProperties.getDescription())
                .termsOfServiceUrl(apiProperties.getTermsOfServiceUrl())
                .contact(new Contact("yex", "http://page.yexy.cool", "yexiyan@zhenhealth.com"))
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }

    private ApiKey apiKey1() {
        return new ApiKey("BearerToken1", "Authorization-x", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    private SecurityContext securityContext1() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth1())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return CollectionUtil.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }

    List<SecurityReference> defaultAuth1() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return CollectionUtil.newArrayList(new SecurityReference("BearerToken1", authorizationScopes));
    }
}
