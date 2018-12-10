package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * className:PowerDao
 * discription:
 * author:wzb
 * createTime:2018-12-09 22:25
 */
public interface PowerDao {

    /**
     * 权限树
     * @return
     */
    @Select("select id,label,parentid,state,icon,url from tb_power where id in(select powerid from tb_role_power where roleid=(( select roleid from tb_emp where username= #{username})) )")
    List<Map> getPowerTree(String username);

}
