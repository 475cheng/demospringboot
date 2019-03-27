package com.bitauto.ep.fx.webapi.configuration;



import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 多数据源，集成druid
 * @author
 */
@Configuration
public class DataConfig {

    @Bean(name = "aftermarket")
    @Qualifier("aftermarket")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.aftermarket")
    public DataSource primaryDataSource(){
        return DruidDataSourceBuilder.create().build();
    }


}
