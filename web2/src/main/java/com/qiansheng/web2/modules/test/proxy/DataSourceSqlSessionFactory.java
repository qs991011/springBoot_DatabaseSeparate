package com.qiansheng.web2.modules.test.proxy;

import com.qiansheng.web2.modules.test.Configure.DataSourceConfiguration;
import com.qiansheng.web2.modules.test.Configure.DataSourceRoute;
import com.qiansheng.web2.modules.test.Configure.DataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@ConditionalOnClass({EnableTransactionManagement.class})
@Import({DataSourceConfiguration.class})
public class DataSourceSqlSessionFactory {

    /**   mybatis 配置路径     */
    @Value("${spring.mybatis.configLocation:mybatis/mybatis.xml}")
    private String MYBATIS_CONFIG;
    /**   mapper路径     */
    @Value("${spring.mybatis.mapperLocations:mapper/*.xml}")
    private String MAPPER_LOCATION;

    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> datasourceType;

//    @Value("${mybatis.type-aliases-package}")
//    private String aliasesPackage;


    @Value("${spring.datasource.readSize}")
    private String dataSourceSize;

    @Resource(name = "writeDataSource")
    private DataSource masterDataSource;


    @Resource(name = "readDataSources")
    private List<DataSource> readDataSources;


    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        log.info("===============init sqlSessionFactory");
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
        sqlSessionFactoryBean.setDataSource(roundRobinDataSourceProxy());
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        //sqlSessionFactoryBean.setTypeAliasesPackage(aliasesPackage);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public AbstractRoutingDataSource roundRobinDataSourceProxy(){
        log.info("===========init robinDataSourceNumber");
        int size = Integer.parseInt(dataSourceSize);
        DataSourceRoute rt = new DataSourceRoute(size);
        Map<Object,Object> targetDataSources = new HashMap();
        targetDataSources.put(DataSourceType.write.getType(),masterDataSource);
        //多个读数据库
        for (int i = 0; i < size; i++) {
            targetDataSources.put(i,readDataSources.get(i));
        }
        rt.setDefaultTargetDataSource(masterDataSource);
        rt.setTargetDataSources(targetDataSources);
        return rt;
    }
}
