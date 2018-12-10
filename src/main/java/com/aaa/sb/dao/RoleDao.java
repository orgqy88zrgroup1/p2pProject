package com.aaa.sb.dao;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import org.mybatis.caches.redis.RedisCache;

import java.util.List;
import java.util.Map;

/**
 * className:RoleDao
 * discription:
 * author:wzb
 * createTime:2018-12-10 18:38
 */
@CacheNamespace(implementation = RedisCache.class)
public interface RoleDao {

    /**
     * 职位列表查询
     * @return
     */
    @Select("select id,role from tb_role")
    List<Map> getRoleList();


}
