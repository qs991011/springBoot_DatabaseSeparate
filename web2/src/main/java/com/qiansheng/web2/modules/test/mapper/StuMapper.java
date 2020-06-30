package com.qiansheng.web2.modules.test.mapper;

import com.qiansheng.web2.modules.test.Entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StuMapper {

    List<Student> findStudent();

    Student findById(int id);
    int insertUser();
}
