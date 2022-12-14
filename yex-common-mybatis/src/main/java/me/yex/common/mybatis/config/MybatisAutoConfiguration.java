

package me.yex.common.mybatis.config;

import me.yex.common.core.resolver.SqlFilterArgumentResolver;
import me.yex.common.mybatis.Injector.RootSqlInjector;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author yex
 * @date 2021/10/10
 * <p>
 * mybatis plus 统一配置
 */
@Configuration(proxyBeanMethods = false)
public class MybatisAutoConfiguration implements WebMvcConfigurer {

	/**
	 * SQL 过滤器避免SQL 注入
	 * @param argumentResolvers
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new SqlFilterArgumentResolver());
	}

	/**
	 * 分页插件, 对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

		//分页
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

		//乐观锁
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		//region 动态表名
		//		DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
//		dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
//			Map<String, Object> paramMap = RequestDataHelper.getRequestData();
//			return RequestDataHelper.getRequestData(tableName);
//		});
//		interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
		//endregion
		return interceptor;
	}

	/**
	 * 审计字段自动填充
	 * @return {@link MetaObjectHandler}
	 */
	@Bean
	public MybatisPlusMetaObjectHandler mybatisPlusMetaObjectHandler() {
		return new MybatisPlusMetaObjectHandler();
	}

	/**
	 * 自定义 SqlInjector
	 * 里面包含自定义的全局方法
	 */
	@Bean
	public RootSqlInjector rootSqlInjector() {
		return new RootSqlInjector();
	}

}
