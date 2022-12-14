

package me.yex.common.swagger.annotation;

import me.yex.common.swagger.config.SwaggerAutoConfiguration;
import me.yex.common.swagger.config.WebSwaggerConfiguration;
import me.yex.common.swagger.support.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启 swagger
 *
 * @author yex
 * @date 2020/10/2
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({ SwaggerAutoConfiguration.class, WebSwaggerConfiguration.class})
public @interface EnableZnSwagger2 {

}
