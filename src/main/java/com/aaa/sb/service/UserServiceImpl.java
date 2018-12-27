package com.aaa.sb.service;

import com.aaa.sb.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * className:UserServiceImpl
 * discription:
 * author:zhangran
 * createTime:2018-12-24 21:51
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Override
    public List<Map> getList(Map map) {
        return userDao.getList(map);
    }

    @Override
    public int getcount(Map map) {
        userDao.getcount(map);
        return Integer.valueOf(userDao.getcount(map).get(0).get("CNT")+"");
    }

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
