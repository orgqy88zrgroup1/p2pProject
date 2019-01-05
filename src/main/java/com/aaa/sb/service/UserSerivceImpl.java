package com.aaa.sb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * className:UserSerivceImpl
 * discription:
 * author:gwd
 * createTime:2018-12-10 20:38
 */
@SuppressWarnings("all")
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
        System.out.println("插入用户登录信息表的map："+map);
        int i = userDao.addUser(map);
        //得到该条信息
        Map info = userDao.info(map);
        int id = Integer.valueOf(info.get("ID")+"");
        System.out.println("外键id是："+id);
        int j = userDao.insertUserAccount(id);
        int k = userDao.insertUserInfo(id);
        if(i==1&&j==1&&k==1){
            System.out.println("插入成功");
            return 1 ;
        }else{
            System.out.println("插入失败");
            return 0;
        }
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

    /**
     * 放入session
     * @param map
     */
    @Override
    public void setSession(Map map, HttpSession session) {
        Map map1 = userDao.getSession(map);
        session.setAttribute("userInfo",map1);
    }

    @Override
    public int checkPhone(String phone) {
        List<Map> map = userDao.checkPhone(phone);
        //说明有重复
        if (map!=null&&map.size()>0){
            return 0;
        }
        return 1;
    }
}
