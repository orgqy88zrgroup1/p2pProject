package com.aaa.sb.service;


import com.aaa.sb.entity.TreeNode;

import java.util.List;
import java.util.Map;

/**
 * className:PowerService
 * discription:
 * author:wzb
 * createTime:2018-12-09 22:25
 */
public interface PowerService {

    /**
     * 权限树
     * @return
     */
    List<TreeNode> getPowerTree();

    /**
     * 权限最终树
     * @return
     */
    List<TreeNode> getPowerEndTree();

    /**
     * 所有树节点
     * @return
     */
    List<TreeNode> getAllTree();

    /**
     * 根据id获取对应树节点
     * @return
     */
    List<Map> getTreeById(Integer id);


    /**
     * 添加
     * @param map
     * @return
     */
    int add(Map map);

    /**
     * 更新
     * @param map
     * @return
     */
    int upd(Map map);

    /**
     * 删除
     * @param id
     * @return
     */
    int del(Integer id);

}
