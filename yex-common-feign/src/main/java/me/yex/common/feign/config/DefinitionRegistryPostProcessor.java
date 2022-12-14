package me.yex.common.feign.config;

import me.yex.common.feign.annotation.ZnFeignService;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author yex
 * @description cn.zhenhealth.health.transaction.config
 */

//@Component
public class DefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, ResourceLoaderAware, EnvironmentAware {
    private Environment environment;
    private ResourceLoader resourceLoader;

    @SneakyThrows
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        //用扫描器根据指定注解进行扫描获取BeanDefinition
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry, false, environment, resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(ZnFeignService.class));
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents("com");
        registerCandidateComponents(beanDefinitionRegistry,candidateComponents);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;

    }

    /**
     * 注册 BeanDefinition
     */
    private void registerCandidateComponents(BeanDefinitionRegistry registry, Set<BeanDefinition> candidateComponents) throws ClassNotFoundException {
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = annotatedBeanDefinition.getMetadata();
                Map<String, Object> customImportAnnotationAttributesMap = annotationMetadata.getAnnotationAttributes(ZnFeignService.class.getName());
                AnnotationAttributes customImportAnnotationAttributes = Optional.ofNullable(AnnotationAttributes.fromMap(customImportAnnotationAttributesMap)).orElseGet(AnnotationAttributes::new);
                //获取注解里的值
                String[] values = customImportAnnotationAttributes.getStringArray("value");
                String className = annotationMetadata.getClassName();
                Class<?> clazzName = Class.forName(className);
//                AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(CustomImportFactoryBean.class)
//                        .addPropertyValue("type", clazzName)
//                        .addPropertyValue("beanName", beanName)
//                        .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
//                        .setRole(BeanDefinition.ROLE_INFRASTRUCTURE)
//                        .getBeanDefinition();
//                registry.registerBeanDefinition(beanName, beanDefinition);
                Arrays.asList(values).forEach(m ->{
                    RootBeanDefinition mbean = null;
                    try {
                        mbean = new RootBeanDefinition(clazzName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    registry.registerBeanDefinition(m, mbean);
                });
            }
        }
    }
}
