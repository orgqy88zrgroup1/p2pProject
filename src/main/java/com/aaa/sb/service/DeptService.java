package com.aaa.sb.service;


import java.util.List;
import java.util.Map;

/**
 * className:DeptService
 * discription:
 * author:wzb
 * createTime:2018-12-09 13:13
 */
public interface DeptService {

    /**
     * 部门列表查询 部门状态为启用
     * @return
     */
    List<Map> getDeptList();

    /**
     * 部门列表查询分页 部门状态为启用
     * 条件查询 部门编号 部门名称 部门状态
     * @return
     */
    Map getDeptPage(Map map);

    /**
     * 部门添加
     * @param map
     * @return
     */
    int addDept(Map map);

    /**
     * 部门更新
     * @param map
     * @return
     */
    int updDept(Map map);

    /**
     * 改变部门状态
     * @param map
     * @return
     */
    int updDeptState(Map map);

    /**
     * 部门删除
     * @param id
     * @return
     */
    int delDept(int id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int batchDelete(String ids);

}
