package com.aaa.sb.dao;

import com.aaa.sb.entity.Emp;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import org.mybatis.caches.redis.RedisCache;

import java.util.List;
import java.util.Map;

/**
 * className:EmpDao
 * discription:
 * author:wzb
 * createTime:2018-12-08 22:06
 */
@CacheNamespace(implementation = RedisCache.class)
public interface EmpDao {

    /**
     * 根据用户名查询对应的用户信息 实体
     * @param userName
     * @return
     */
    @Select(value = "select * from tb_emp where username = #{userName}")
    Emp findByUserName(String userName);

    /**
     * 根据用户名查询对应的用户信息 Map
     * @param userName
     * @return
     */
    @Select(value = "select * from tb_emp where username=#{userName}")
    List<Map> selByUserName(String userName);

    /**
     * 根据用户id查询对应的用户信息
     * @param id
     * @return
     */
    @Select(value = "select * from tb_emp where id = #{id}")
    Emp findByUserId(int id);

    /**
     * 根据用户id查询对应的用户信息
     * @param id
     * @return
     */
    @Select(value = "select * from tb_emp where id = #{id}")
    List<Map> selByUserId(int id);

}
