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

}
