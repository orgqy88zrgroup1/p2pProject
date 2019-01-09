package com.aaa.sb.dao;

import com.aaa.sb.entity.Emp;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * className:EmpDao
 * discription:
 * author:wzb
 * createTime:2018-12-08 22:06
 */
/*@CacheNamespace(implementation = RedisCache.class)*/
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

    /**
     * 根据部门编号查询员工信息
     * @param deptno
     * @return
     */
    @Select(value = "select * from tb_emp where deptno = #{deptno}")
    List<Map> selByDeptNo(int deptno);

    /**
     * 查询所有用户名
     * @return
     */
    @Select("select username from tb_emp")
    List<Map> selUsernameOfList();

    /**
     * 员工带参分页查询
     * @param map
     * @return
     * 如果使用注解方式，动态sql必须在标签<script></script>
     * 如果使用<script></script>标签，大于小于 必须使用 &gt;/&lt;
     */
    /*@Select("<script>select id,username,password,roleid,empname,deptno,sex,hiredate,sal,comm,idcard,tel from \n" +
            "(select rownum rn,id,username,password,roleid,empname,deptno,sex,to_char(hiredate,'yyyy-MM-dd') hiredate,sal,comm,idcard,tel from tb_emp\n" +
            "where rownum &lt; #{end}   " +
            "<if test=\"empname!=null and empname!=''\"> and empname like '%'||#{empname}||'%'</if>" +
            "<if test=\"deptno!=null and deptno!=''\">  and deptno =#{deptno}</if>" +
            " )a where a.rn &gt; #{start} </script>")*/
    @Select("<script>select role,dname,id,username,password,roleid,empname,deptno,sex,hiredate,sal,comm,idcard,tel from \n" +
            "(select rownum rn,tr.role role,td.dname dname,te.id id,te.username username,te.password password,te.roleid roleid,te.empname empname,te.deptno deptno,te.sex sex,to_char(te.hiredate,'yyyy-MM-dd') hiredate,te.sal sal,te.comm comm,te.idcard idcard,te.tel tel from tb_emp te left join tb_role tr on tr.id = te.roleid left join tb_dept td on td.id = te.deptno\n" +
            "where rownum &lt; #{end}   " +
            "<if test=\"empname!=null and empname!=''\"> and empname like '%'||#{empname}||'%'</if>" +
            "<if test=\"deptno!=null and deptno!=''\">  and deptno =#{deptno}</if>" +
            " )a where a.rn &gt; #{start} </script>")
    List<Map> getPageByParam(Map map);

    /**
     * 员工查询分页总数量
     * @param map
     * @return
     */
    @Select("<script> select count(*) from tb_emp <where>" +
            "<if test=\"empname!=null and empname!=''\"> and empname like '%'||#{empname}||'%'</if>" +
            "<if test=\"deptno!=null and deptno!=''\">  and deptno =#{deptno}</if>" +
            " </where></script>")
    int getPageCount(Map map);

    /**
     * 批量删除
     * @param list
     * @return
     */
    @Delete("<script>delete from tb_emp where id in" +
            "<foreach collection='list' item='en' open='(' close=')' separator=','>#{en}</foreach></script>")
    int batchDelete(List list);

    /**
     * 添加
     * @param map
     * @return
     */
    @Insert("insert into tb_emp values(tb_emp_id.nextval,#{USERNAME},#{PASSWORD},#{ROLEID},#{EMPNAME},#{DEPTNO},#{SEX},to_date(substr(#{HIREDATE},1,10),'yyyy-mm-dd'),#{SAL},#{COMM},#{IDCARD},#{TEL})")
    int add(Map map);

    /**
     * 更新
     * @param map
     * @return
     */
    @Update("update tb_emp set USERNAME=#{USERNAME},PASSWORD=#{PASSWORD},ROLEID=#{ROLEID},EMPNAME=#{EMPNAME},DEPTNO=#{DEPTNO},SEX=#{SEX},HIREDATE=to_date(substr(#{HIREDATE},1,10),'yyyy-mm-dd'),SAL=#{SAL},COMM=#{COMM},IDCARD=#{IDCARD},TEL=#{TEL} where ID=#{ID}")
    int upd(Map map);

    /**
     * 根据部门编号更新角色状态为7不可用
     * @param map
     * @return
     */
    @Update("update tb_emp set roleid=7 where deptno=#{deptno}")
    int updRoleId(Map map);

    /**
     * 根据员工id更新角色状态为之前的状态
     * @param map
     * @return
     */
    @Update("update tb_emp set roleid=#{ROLEID} where id=#{EMPID}")
    int updRoleIdUse(Map map);

    /**
     * 删除表tb_emp_dept部门启用的字段
     * @param map
     * @return
     */
    @Delete("delete from tb_emp_dept where id = #{ID}")
    int delEmpDeptId(Map map);

    /**
     * 根据部门编号查询当前id与roleid返回一个list
     * @param map
     * @return
     */
    @Select("select id,roleid,deptno from tb_emp where deptno = #{deptno}")
    List<Map> selRoleIdUse(Map map);


    /**
     * 删除
     * @param id
     * @return
     */
    @Delete("delete from tb_emp where id = #{ID}")
    int del(Integer id);


    /**
     * 添加禁用字段id roleid
     * @param map
     * @return
     */
    @Insert("insert into tb_emp_dept (id,empid,roleid,deptno)values(seq_emp_dept_id.nextval,#{ID},#{ROLEID},#{DEPTNO})")
    int addStopUseIdAndRoleId(Map map);

    /**
     * 根据部门编号查询当前id与roleid返回一个list
     * @param map
     * @return
     */
    @Select("select id,deptno,empid,roleid from tb_emp_dept where deptno = #{deptno}")
    List<Map> selEmpDeptIdAndRoleId(Map map);


}
