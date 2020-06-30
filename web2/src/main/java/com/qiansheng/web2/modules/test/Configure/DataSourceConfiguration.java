package com.qiansheng.web2.modules.test.Configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class


DataSourceConfiguration {

    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "writeDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource writeDataSource(){
        log.info("...writeDataSource init.....");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "readDataSource1")
    @ConfigurationProperties(prefix = "spring.slave")
    public DataSource readSourceOne(){
        log.info("........readDataSourceOne init..............");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "readDataSource2")
    @ConfigurationProperties(prefix = "spring.read2")
    public DataSource readSourceTwo(){
        log.info("........readDataSourcetwo init..............");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean("readDataSources")
    public List<DataSource> readDataSources() {
        List<DataSource> dataSources=new ArrayList<>();
        dataSources.add(readSourceOne());
        dataSources.add(readSourceTwo());
        return dataSources;
    }

}
