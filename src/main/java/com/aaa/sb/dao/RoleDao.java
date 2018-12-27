package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * className:RoleDao
 * discription:
 * author:wzb
 * createTime:2018-12-10 18:38
 */
/*@CacheNamespace(implementation = RedisCache.class)*/
public interface RoleDao {

    /**
     * 职位列表查询
     * @return
     */
    @Select("select id,role from tb_role")
    List<Map> getRoleList();

    /**
     * 角色列表查询分页
     * 条件查询
     * @return
     */
    @Select("<script>select id,role from tb_role" +
            "<where> 1 = 1 and id !=7" +
            "<if test=\"role!=null and role!=''\"> and role like '%'||#{role}||'%'</if>" +
            "</where></script>")
    List<Map> getRolePage(Map map);

    /**
     * 角色添加
     * @param map
     * @return
     */
    @Insert(value = "insert into tb_role (id,role)values(tb_role_id.nextval,#{ROLE})")
    int addRole(Map map);

    /**
     * 角色更新
     * @param map
     * @return
     */
    @Update(value = "update tb_role set role=#{ROLE} where id=#{ID}")
    int updRole(Map map);

    /**
     * 角色删除
     * @param id
     * @return
     */
    @Delete(value = "delete from tb_role where id=#{id}")
    int delRole(int id);

    /**
     * 删除角色关联的权限
     * @param
     * @return
     */
    @Delete(value = "delete from tb_role_power where roleid=#{roleid}")
    int delRoleAndPower(int roleid);

    /**
     * 批量添加 tb_role_power
     * @param list
     * @return
     */
    @Insert("<script>insert into tb_role_power (id,roleid,powerid) select tb_role_power_id.nextval,a.* from " +
            "(<foreach collection=\"list\"  separator=\"union all\" item=\"roleMap\">select #{roleMap.roleid},#{roleMap.powerid} from dual</foreach>) a </script>")
    int batchAdd(List list);

    /**
     * 根据roleid查询tb_power表具体的数据
     * @return
     */
    @Select("select tb.id,tb.label,tb.icon,tb.url,tb.parentid from tb_power tb left join tb_role_power tp on tp.powerid = tb.id where tp.roleid = #{id} ")
    List<Map> selRoleIdAndPowerId(Map map);


}
