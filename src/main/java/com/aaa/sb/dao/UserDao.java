package com.aaa.sb.dao;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import org.mybatis.caches.redis.RedisCache;

import java.util.List;
import java.util.Map;

/**
 * className:UserDao
 * discription:
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
}
