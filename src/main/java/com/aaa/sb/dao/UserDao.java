package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * className:UserDao
 * discription:
 * author:gwd
 * createTime:2018-12-10 20:34
 */
public interface UserDao {

    /**
     * 得到用戶列表
     * @return
     */
    @Select(value = "select userName,password,telephone from user_login_info")
    List<Map> getUserList();

    /**
     * 用户注册时 添加到用户登录信息表
     * @param map
     * @return
     */
    @Insert(value = "insert into user_login_info values(seq_user_login_info_id.nextval,#{username},#{password},#{phone})")
    int addUser(Map map);

    /**
     * 根据用户名查密码
     * @param userName
     * @return
     */
    @Select(value = "select password from user_login_info where username=#{userName}")
    Map checkPwd(String userName);
}
