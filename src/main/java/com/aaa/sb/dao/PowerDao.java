package com.aaa.sb.dao;

import com.aaa.sb.entity.TreeNode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * className:PowerDao
 * discription:
 * author:wzb
 * createTime:2018-12-09 22:25
 */
/*@CacheNamespace(implementation = RedisCache.class)*/
public interface PowerDao {

    /**
     * 权限树
     * @return
     */
    @Select("select id,label,parentid,state,icon,url from tb_power where id in(select powerid from tb_role_power where roleid=(( select roleid from tb_emp where username= #{username})) )")
    List<Map> getPowerTree(String username);

    /**
     * 所有树节点
     * @return
     */
    @Select("select id,label,parentid,state,icon,url from tb_power")
    List<TreeNode> getAllTree();


    /**
     * 根据id获取对应树节点
     * @return
     */
    @Select("select id,label,parentid,state,icon,url from tb_power where id = {ID}")
    List<Map> getTreeById(Integer id);


    /**
     * 添加
     * @param map
     * @return
     */
    @Insert("insert into tb_power (id,label,state,icon,url,parentid)values(TB_POWER_ID.nextval,#{label},1,#{icon},#{url},#{parentId})")
    int add(Map map);

    /**
     * 更新
     * @param map
     * @return
     */
    @Update("update tb_power set LABEL=#{label},ICON=#{icon},URL=#{url},PARENTID=#{parentId} where ID=#{id}")
    int upd(Map map);

    /**
     * 删除
     * @param id
     * @return
     */
    @Delete("delete from tb_power where id=#{id}")
    int del(Integer id);

}
