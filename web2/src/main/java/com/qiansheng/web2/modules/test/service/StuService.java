package com.qiansheng.web2.modules.test.service;

import com.qiansheng.web2.modules.test.Entity.Student;

import java.util.List;

public interface StuService {
    public Student findById(int id);
    public List<Student> findStudent();
    int insertUser();
}
