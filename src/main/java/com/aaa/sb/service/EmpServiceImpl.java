package com.aaa.sb.service;

import com.aaa.sb.dao.EmpDao;
import com.aaa.sb.entity.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * className:EmpServiceImpl
 * discription:
 * author:wzb
 * createTime:2018-12-08 22:19
 */
@Service
public class EmpServiceImpl implements EmpService{

    @Autowired
    private EmpDao empDao;

    @Override
    public Emp findByUserName(String userName) {
        return empDao.findByUserName(userName);
    }

    @Override
    public List<Map> selByUserName(String userName) {
        return empDao.selByUserName(userName);
    }

    @Override
    public Emp findByUserId(int id) {
        return empDao.findByUserId(id);
    }

    @Override
    public List<Map> selByUserId(int id) {
        return empDao.selByUserId(id);
    }
}
