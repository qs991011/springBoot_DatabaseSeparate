package com.qiansheng.web2.modules.test.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class Student implements Serializable {

    @Getter @Setter
    private int id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private float sumScore;
    @Getter @Setter
    private int age;
}
