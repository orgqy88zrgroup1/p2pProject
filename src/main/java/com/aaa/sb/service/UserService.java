package com.aaa.sb.service;
import java.util.List;
import java.util.Map;

/**
 * className:UserService
 * discription:
 * author:gwd
 * createTime:2018-12-10 20:37
 */
public interface UserService {
    /**
     * 得到用戶列表
     * @return
     */
    List<Map> getUserList();

    /**
     * 用户注册时 添加到用户登录信息表
     * @param map
     * @return
     */
    int addUser(Map map);

    /**
     * 根据用户名查密码
     * @param userName
     * @return
     */
    int  checkPwd(String userName,String password);
}
