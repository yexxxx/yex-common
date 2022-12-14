
package me.yex.common.api.props;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.util.ArrayList;
import java.util.List;

/**
 * SwaggerProperties
 *api:
 *   enable: false
 *   title: 用户机构档案-接口文档
 *   description: api doc
 *   group-name: 用户机构档案
 *   base-package: cn.zhenhealth.health
 *
 *
 *
 * knife4j:
 *   # 开启增强配置
 *   enable: true
 *   # 开启生产环境屏蔽
 *   production: false
 *
 * @author yex
 * @date 2021/10/10
 */
@Data
@ConfigurationProperties("api")
public class ApiProperties {
    private boolean enable;

    private String title;

    private String description;

    private String termsOfServiceUrl;

    private String groupName;

    private String basePackage;

    private List<Group> groups;

    @Data
    public class Group{
        private String name;

        private String basePackage;
    }

}
