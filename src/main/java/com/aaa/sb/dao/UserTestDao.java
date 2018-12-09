package com.aaa.sb.dao;

import com.aaa.sb.entity.UserTest;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import org.mybatis.caches.redis.RedisCache;

import java.util.List;
import java.util.Map;

/**
 * className:UserTestDao
 * discription:
 * author:wzb
 * createTime:2018-11-30 20:09
 */
@CacheNamespace(implementation = RedisCache.class)
public interface UserTestDao {

    /**
     * 根据用户名查询对应的用户信息 实体
     * @param userName
     * @return
     */
    @Select(value = "select * from usertest where username = #{userName}")
    UserTest findByUserName(String userName);

    /**
     * 根据用户名查询对应的用户信息 Map
     * @param userName
     * @return
     */
    @Select(value = "select * from usertest where username=#{userName}")
    List<Map> selByUserName(String userName);

    /**
     * 根据用户id查询对应的用户信息
     * @param userId
     * @return
     */
    @Select(value = "select * from usertest where userid = #{userId}")
    UserTest findByUserId(int userId);

    /**
     * 根据用户id查询对应的用户信息
     * @param userId
     * @return
     */
    @Select(value = "select * from usertest where userid = #{userId}")
    List<Map> selByUserId(int userId);

}
