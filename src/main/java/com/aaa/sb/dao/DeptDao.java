package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * className:DeptDao
 * discription:
 * author:wzb
 * createTime:2018-12-09 13:13
 */
/*@CacheNamespace(implementation = RedisCache.class)*/
public interface DeptDao {

    /**
     * 部门列表查询 部门状态为启用
     * @return
     */
    @Select("select id,dname,des,state from tb_dept where state = 1")
    List<Map> getDeptList();

    /**
     * 部门列表查询分页 部门状态为启用
     * 条件查询 部门编号 部门名称 部门状态
     * @return
     */
    @Select("<script>select id,dname,des,state from tb_dept" +
            "<where>" +
            "<if test=\"id!=null and id!=''\"> and id=#{id}</if>" +
            "<if test=\"dname!=null and dname!=''\"> and dname like '%'||#{dname}||'%'</if>" +
            "</where></script>")
    List<Map> getDeptPage(Map map);

    /**
     * 部门添加
     * @param map
     * @return
     */
    @Insert(value = "insert into tb_dept (id,dname,des,state)values(tb_dept_id.nextval,#{DNAME},#{DES},#{STATE})")
    int addDept(Map map);

    /**
     * 部门更新
     * @param map
     * @return
     */
    @Update(value = "update tb_dept set dname=#{DNAME},des=#{DES} where id=#{ID}")
    int updDept(Map map);

    /**
     * 根据部门编号修改部门状态不可用
     * @param map
     * @return
     */
    @Update(value = "update tb_dept set state=2 where id=#{deptno}")
    int updDeptState1(Map map);

    /**
     * 根据部门编号修改部门状态可用
     * @param map
     * @return
     */
    @Update(value = "update tb_dept set state=1 where id=#{deptno}")
    int updDeptState2(Map map);

    /**
     * 部门删除
     * @param id
     * @return
     */
    @Delete(value = "delete from tb_dept where id=#{id}")
    int delDept(int id);


    /**
     * 批量删除
     * @param list
     * @return
     */
    @Delete("<script>delete from tb_dept where id in" +
            "<foreach collection='list' item='en' open='(' close=')' separator=','>#{en}</foreach></script>")
    int batchDelete(List list);




}
