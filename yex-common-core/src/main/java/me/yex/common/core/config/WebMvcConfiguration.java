

package me.yex.common.core.config;

import cn.hutool.core.date.DatePattern;
import me.yex.common.core.jackson.ZnJavaTimeModule;
import me.yex.common.core.resolver.CommonLocaleResolver;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

/**
 * @author yex
 * @date 2021/10/10
 * <p>
 */
@Configuration(proxyBeanMethods = false)
//@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 9)
@ConditionalOnWebApplication(type = SERVLET)
public class WebMvcConfiguration implements WebMvcConfigurer {

	/**
	 * 增加GET请求参数中时间类型转换 {@link ZnJavaTimeModule}
	 * <ul>
	 * <li>HH:mm:ss -> LocalTime</li>
	 * <li>yyyy-MM-dd -> LocalDate</li>
	 * <li>yyyy-MM-dd HH:mm:ss -> LocalDateTime</li>
	 * </ul>
	 * @param registry
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
		registrar.setTimeFormatter(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN));
		registrar.setDateFormatter(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
		registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
		registrar.registerFormatters(registry);
	}


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOriginPatterns("*")
				.allowedHeaders("*")
				.allowedMethods("*")
				.allowCredentials(true)
				.maxAge(3600);
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new CommonLocaleResolver();
	}

//	@Bean
//	public HttpErrorController httpErrorController(){
//		return new HttpErrorController();//自定义的错误和filter异常处理控制器
//	}
//
//
//	@Bean
//	public ExceptionFilter exceptionFilter(){
//		return new ExceptionFilter();//自定义的过滤器
//	}
//
//	// 捕获filter异常
//	@Bean
//	public FilterRegistrationBean<ExceptionFilter> exceptionFilterRegistration() {
//		FilterRegistrationBean<ExceptionFilter> registration = new FilterRegistrationBean<>();
//		registration.setFilter(exceptionFilter());
//		registration.setName("exceptionFilter");
//		//此处尽量小，要比其他Filter靠前
//		registration.setOrder(-1);
//		return registration;
//	}

}
