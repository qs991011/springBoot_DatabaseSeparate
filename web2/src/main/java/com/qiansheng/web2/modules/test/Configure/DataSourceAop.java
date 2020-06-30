package com.qiansheng.web2.modules.test.Configure;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DataSourceAop {
    @Before("execution(* com.qiansheng.web2.modules.test.mapper..*.find*(..)) "
            + " || execution(* com.qiansheng.web2.modules.test.mapper..*.get*(..)) "
            + " || execution(* com.qiansheng.web2.modules.test.mapper..*.query*(..))")
    public void setReadDataSourceType() {
        DataSourceContextHolder.read();
        log.info("dataSource 切换到：Read");
    }

    @Before("execution(* com.qiansheng.web2.modules.test.mapper..*.insert*(..)) "
            + " || execution(* com.qiansheng.web2.modules.test.mapper..*.update*(..))"
            + " || execution(* com.qiansheng.web2.modules.test.mapper..*.add*(..))")
    public void setWriteDataSourceType() {
        DataSourceContextHolder.write();
        log.info("dataSource 切换到：Write");
    }

    @After("execution(* com.qiansheng.web2.modules.test.mapper..*.*(..))")
    public void remove(){
        DataSourceContextHolder.clear();
        log.info("dataSource clear");
    }
}
