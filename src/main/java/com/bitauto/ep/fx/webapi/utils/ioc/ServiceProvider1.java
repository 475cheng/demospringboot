/*
package com.bitauto.ep.fx.webapi.utils.ioc;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.type.StandardMethodMetadata;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

*/
/**
 *  IOC容器获取Bean帮助类
 *  @author  zhanglei
 *//*

@Component
public class ServiceProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static ConfigurableApplicationContext configurableApplicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(this.applicationContext == null) {
            this.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static List<String> getBeansWithAnnotation( Class<? extends Annotation> type ) {
        Predicate<Map<String, Object>> filter = Predicates.alwaysTrue();
        return getBeansWithAnnotation( type, filter );
    }

    public static List<String> getBeansWithAnnotation( Class<? extends Annotation> type, Predicate<Map<String, Object>> attributeFilter ) {
        configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        List<String> result = Lists.newArrayList();

        ConfigurableListableBeanFactory factory = configurableApplicationContext.getBeanFactory();
        for( String name : factory.getBeanDefinitionNames() ) {
            BeanDefinition bd = factory.getBeanDefinition( name );

            if( bd.getSource() instanceof StandardMethodMetadata) {
                StandardMethodMetadata metadata = (StandardMethodMetadata) bd.getSource();

                Map<String, Object> attributes = metadata.getAnnotationAttributes( type.getName() );
                if( null == attributes ) {
                    continue;
                }

                if( attributeFilter.apply( attributes ) ) {
                    result.add( name );
                }
            }
        }

        return result;
    }

    //通过name获取 Bean.
    public static Object getService(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getService(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getService(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

}
*/
