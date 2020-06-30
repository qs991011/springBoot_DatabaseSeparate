package com.qiansheng.web2.modules.test.Configure;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataSourceContextHolder {

    @Getter
    private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public static void read() {
        log.debug("读操作");
        local.set(DataSourceType.read.getType());
    }

    public static void write() {
        log.debug("写操作");
        local.set(DataSourceType.write.getType());
    }

    public static String getReadOrWrite() {
        return local.get();
    }

    public static void clear(){
        local.remove();
    }
}
