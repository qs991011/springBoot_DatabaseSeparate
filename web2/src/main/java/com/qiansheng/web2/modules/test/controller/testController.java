package com.qiansheng.web2.modules.test.controller;

import com.qiansheng.web2.modules.test.Entity.Student;
import com.qiansheng.web2.modules.test.service.StuService;
import com.qiansheng.web2.modules.test.service.impl.StudentServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class testController {
    @RequestMapping(value = "hello",method = RequestMethod.GET)
    public String hello(){
        return "hello world";
    }
    @Autowired
    private StudentServiceImpl stuService;

    @RequestMapping("/user")
    public List<Student> getUser(){
       List<Student>  st = stuService.findStudent();
        return  st;
    }

    @RequestMapping("/user/{id}")
    public Student getUserById(@PathVariable int id){
        return stuService.findById(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String  add() {
        stuService.insertUser();
        return "1";
    }
}
