package com.qiansheng.web2.modules.test.Configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class DataSourceRoute extends AbstractRoutingDataSource {

    private final int dataSourceNumber;

    private AtomicInteger count = new AtomicInteger(0);
    public DataSourceRoute(int dataSourceNumber){
        this.dataSourceNumber = dataSourceNumber;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DataSourceContextHolder.getReadOrWrite();
        log.info("========== switch dataSource: " + typeKey);
        if (typeKey.equals(DataSourceType.write.getType())){
            return DataSourceType.write.getType();
        }else{
            //从数据源随机分配
            int number = count.getAndAdd(1);
            int slaveDsIndex = number % dataSourceNumber;
            return new Integer(slaveDsIndex);
        }
    }
}
