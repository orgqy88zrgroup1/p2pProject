package com.aaa.sb.dao;

<<<<<<< HEAD
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
=======
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import org.mybatis.caches.redis.RedisCache;
>>>>>>> 3e01e3348973a38c523d0622e9fd91e918c0fba2

import java.util.List;
import java.util.Map;

/**
 * className:UserDao
 * discription:
<<<<<<< HEAD
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
=======
 * author:zhangran
 * createTime:2018-12-24 21:52
 */

public interface UserDao {

    @Select(value="<script>select * from " +
            "(select rownum rn,id,userid,bidamount,bidcurrentamount,bidrepaymentmethod,bidrate,biddeadline,bidissuedate,biddeaddate,bidapplydate,biddesc,bidtype,bidstate " +
            "from bid_info where rownum &lt; #{end} " +
            "<if test=\"BIDTYPE!=null and BIDTYPE!=''\">and BIDTYPE=#{BIDTYPE}</if>" +
            "<if test=\"BIDDEADLINE!=null and BIDDEADLINE!=''\">and BIDDEADLINE=#{BIDDEADLINE}</if>" +
            ")a where a.rn &gt; #{start}</script>")
    List<Map> getList(Map map);

    @Select(value="<script>select count(*) cnt from bid_info where 1=1" +
            "<if test=\"BIDTYPE!=null and BIDTYPE!=''\">and BIDTYPE=#{BIDTYPE}</if>" +
            "<if test=\"BIDDEADLINE!=null and BIDDEADLINE!=''\">and BIDDEADLINE=#{BIDDEADLINE}</if></script>")
    List<Map> getcount(Map map);
>>>>>>> 3e01e3348973a38c523d0622e9fd91e918c0fba2
}
