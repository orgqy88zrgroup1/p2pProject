package com.aaa.sb.dao;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import org.mybatis.caches.redis.RedisCache;

import java.util.List;
import java.util.Map;

/**
 * className:DeptDao
 * discription:
 * author:wzb
 * createTime:2018-12-09 13:13
 */
@CacheNamespace(implementation = RedisCache.class)
public interface DeptDao {

    /**
     * 部门列表查询 部门状态为启用
     * @return
     */
    @Select("select id,dname,des,state from dept where state = 1")
    List<Map> getDeptList();



}
