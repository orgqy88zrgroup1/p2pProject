package com.aaa.sb.service;

import com.aaa.sb.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * className:UserSerivceImpl
 * discription:
 * author:gwd
 * createTime:2018-12-10 20:38
 */
@Service
public class UserSerivceImpl implements UserService{

    @Autowired
    private UserDao userDao;


    @Override
    public List<Map> getUserList() {
        return userDao.getUserList();
    }

    @Override
    public int addUser(Map map) {
        return userDao.addUser(map);
    }

    @Override
    public int checkPwd(String userName,String password) {
        //System.out.println(userName+"password:"+password);
        Map map = userDao.checkPwd(userName);
        //System.out.println("根据用户名取的map："+map);
        if((map.get("PASSWORD")+"").equals(password)){
            //说明密码正确
            return 1;
        }
        return 0;
    }
}
