package com.qiansheng.web2.modules.test.Configure;

import lombok.Getter;
import lombok.Setter;

public enum DataSourceType {

    read("read", "从库"),
    write("write", "主库");

    @Setter @Getter
    private String type;

    @Setter @Getter
    private String name;
    DataSourceType(String type, String name)
    {
        this.type = type;
        this.name = name;
    }

}
