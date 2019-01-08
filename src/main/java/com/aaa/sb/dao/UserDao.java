package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Select(value = "select * from user_login_info l,user_account a where l.ID=#{userID} and a.userID=#{userID}")
    List<Map> getAccountList(Map map);

    @Update(value="update user_account set availableBalance = #{availableBalance} where userId=#{userID}")
    int getRecharge(Map map);

    @Select(value="select id,userid,availableBalance from user_account where userID=#{userID}")
    Map getAvailableBalance(Map map);

    @Insert(value="insert into user_account_flow values (seq_user_account_flow_id.nextval,#{USERID},#{ID},#{changeCount},#{availableBalance},sysdate,#{state})")
    int getAccountFlow(Map map);

    @Update(value="update user_account set availableBalance = #{availableBalance} where userId=#{userID}")
    int getCash(Map map);

    @Update(value="update user_account set availableBalance = #{availableBalance} where userId=#{userID}")
    int getRepayment(Map map);

    @Select(value="select id,bidNextRepayAmount from bid_repay_info where bidRepayUserID=#{userID} and bidRepayState=1 and bidRepayDate<sysdate and bidRepayDeadDate>sysdate")
    Map getRepayInfo(Map map);

    @Update(value="update bid_repay_info set bidRepayState=2 where bidRepayUserID=#{userID} and bidRepayDate<sysdate and bidRepayDeadDate>sysdate")
    Map getRepayState(Map map);

    @Update(value="update bid_repay_info set bidRepayState = 2 where id=#{bidRepayState}")
    int getRepay(Map map);
}
