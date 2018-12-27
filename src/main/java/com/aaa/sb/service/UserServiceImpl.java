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
    UserDao userdao;

    @Override
    public List<Map> getList(Map map) {
        return userdao.getList(map);
    }

    @Override
    public int getcount(Map map) {
        userdao.getcount(map);
        System.out.println(userdao.getcount(map).get(0).get("CNT")+"");
        return Integer.valueOf(userdao.getcount(map).get(0).get("CNT")+"");
    }


}
