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

    /**
     * 放入session
     * @param map
     */
    @Select("select id,userName,password,telephone from user_login_info where username = #{username}")
    Map getSession(Map map);

    /**
     * 给注册的新用户开户
     * @return
     */
    @Insert(value = "insert into user_account values(seq_user_account_id.nextval,#{id},0,0,0,0,0,50000,50000,123456)")
    int insertUserAccount(int id);

    /**
     * 根据map得到该用户的所有注册信息
     * @param map
     */
    @Select("select id,userName,password,telephone from user_login_info where username = #{username}")
    Map info(Map map);

    /**
     * 查看电话号码是否重复
     * @param phone
     * @return
     */
    @Select(value = "select id,username,password,telephone from user_login_info where telephone=#{phone}")
    List<Map> checkPhone(String phone);

    /**
     * 注册时将用户的id插入到user_info表中去
     * @param id
     * @return
     */
    @Insert(value = "insert into user_info(id,userId) values(seq_userinfo_id.nextval,#{id})")
    int insertUserInfo(int id);
}
