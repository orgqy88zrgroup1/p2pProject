package com.aaa.sb.service;


import com.aaa.sb.entity.TreeNode;

import java.util.List;

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

}
