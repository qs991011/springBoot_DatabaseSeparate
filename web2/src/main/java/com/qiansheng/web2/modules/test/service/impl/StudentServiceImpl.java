package com.qiansheng.web2.modules.test.service.impl;

import com.qiansheng.web2.modules.test.Entity.Student;
import com.qiansheng.web2.modules.test.mapper.StuMapper;
import com.qiansheng.web2.modules.test.service.StuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StuService {
    @Autowired
    private StuMapper tstuMapper;

    @Override
    public Student findById(int id) {
        return  tstuMapper.findById(id);
    }

    @Override
    public List<Student> findStudent() {
       return tstuMapper.findStudent();
    }

    @Override
    @Transactional(value="transactionManager")
    public int insertUser() {
        try{
            tstuMapper.insertUser();
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return 1;
    }
}
