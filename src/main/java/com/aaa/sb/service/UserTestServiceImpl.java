package com.aaa.sb.service;

import com.aaa.sb.dao.UserTestDao;
import com.aaa.sb.entity.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * className:UserTestServiceImpl
 * discription:
 * author:wzb
 * createTime:2018-11-30 20:28
 */
@Service
public class UserTestServiceImpl implements UserTestService {

    @Autowired
    private UserTestDao userTestDao;

    /**
     * 根据用户名查询对应的用户信息 实体
     * @param userName
     * @return
     */
    @Override
    public UserTest findByUserName(String userName) {
        return userTestDao.findByUserName(userName);
    }

    /**
     * 根据用户名查询对应的用户信息 Map
     * @param userName
     * @return
     */
    @Override
    public List<Map> selByUserName(String userName) {
        System.out.println(userName);
        return userTestDao.selByUserName(userName);
    }

    /**
     * 根据用户id查询对应的用户信息
     * @param id
     * @return
     */
    @Override
    public UserTest findByUserId(int id) {
        return userTestDao.findByUserId(id);
    }

    /**
     * 根据用户id查询对应的用户信息
     * @param id
     * @return
     */
    @Override
    public List<Map> selByUserId(int id) {
        return userTestDao.selByUserId(id);
    }
}
