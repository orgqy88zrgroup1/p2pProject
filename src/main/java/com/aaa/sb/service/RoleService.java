package com.aaa.sb.service;

import java.util.List;
import java.util.Map;

/**
 * className:RoleService
 * discription:
 * author:wzb
 * createTime:2018-12-10 18:42
 */
public interface RoleService {

    /**
     * 职位列表查询
     * @return
     */
    List<Map> getRoleList();

    /**
     * 角色列表查询分页
     * 条件查询
     * @return
     */
    Map getRolePage(Map map);

    /**
     * 角色添加
     * @param map
     * @return
     */
    int addRole(Map map);

    /**
     * 角色更新
     * @param map
     * @return
     */
    int updRole(Map map);

    /**
     * 角色删除
     * @param id
     * @return
     */
    int delRole(int id);

    /**
     * 批量添加 tb_role_power
     * @param map
     * @return
     */
    int batchAdd(Map map);

    /**
     * 根据roleid查询tb_power表具体的数据
     * @return
     */
    List<Map> selRoleIdAndPowerId(Map map);


}
