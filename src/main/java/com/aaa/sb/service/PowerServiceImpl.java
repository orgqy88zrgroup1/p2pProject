package com.aaa.sb.service;

import com.aaa.sb.dao.PowerDao;
import com.aaa.sb.entity.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * className:PowerServiceImpl
 * discription:
 * author:wzb
 * createTime:2018-12-09 22:25
 */
@Service
public class PowerServiceImpl implements PowerService {

    @Autowired
    private PowerDao powerDao;
    @Autowired
    private HttpSession session;

    /**
     * 得到List<TreeNode>集合
     * @return
     */
    @Override
    public List<TreeNode> getPowerTree() {
        //根据不同的用户名，查到角色id，在查询到该角色所对应的权限
        String username = session.getAttribute("username")+"";
        List<Map> powerMapList = powerDao.getPowerTree(username);
        //定义返回列表
        List<TreeNode> powerList = new ArrayList<TreeNode>();
        //判断是否为空
        if(powerMapList!=null&&powerMapList.size()>0){
            TreeNode treeNode = null;
            //循环遍历查询出来的权限，构造TreeNode对象，加入powerList，构造该角色能够看到的树
            for(Map powerMap:powerMapList){
                // treeNode = new TreeNode(id, text, parentId, state, iconCls, url);
                treeNode = new TreeNode(Integer.valueOf(powerMap.get("ID")+""), powerMap.get("LABEL")+"",
                        Integer.valueOf(powerMap.get("PARENTID")+""), powerMap.get("STATE")+""
                        , powerMap.get("ICON")+"", powerMap.get("URL")+"");
                powerList.add(treeNode);
            }
        }
        return powerList;
    }

    /**
     * 添加子节点
     * @return
     */
    @Override
    public List<TreeNode> getPowerEndTree() {
        //得到不同用户所能看到的所有节点
        List<TreeNode> powerAllList = getPowerTree();
        //临时的powerList
        List<TreeNode> powerTempList = new ArrayList<TreeNode>();
        //判断是否为空
        if(powerAllList!=null&&powerAllList.size()>0){
            for(TreeNode ptreeNode:powerAllList){
                if(ptreeNode.getParentId()==0){//如果等于0,说明是一级节点，将所有的父节点先加入到树里
                    powerTempList.add(ptreeNode);
                    //在递归绑定子节点，慢慢将树都加入
                    bindChirldren(ptreeNode,powerAllList);
                }
            }
        }
        return powerTempList;
    }

    /**
     * 递归绑定所有子节点
     * @param parentTreeNode
     * @param powerAllList
     */
    private void bindChirldren(TreeNode parentTreeNode,List<TreeNode> powerAllList ){
        for(TreeNode chirldrenTreeNode:powerAllList){
            //如果自己的节点等于其他节点的父节点id，说明自己是其他节点的父节点
            if(parentTreeNode.getId()==chirldrenTreeNode.getParentId()){
                //获取当前节点的所有子节点集合
                List<TreeNode> children = parentTreeNode.getChildren();
                if(children==null){//如果孩子节点为空，
                    List<TreeNode> childrenTempList = new ArrayList<TreeNode>();//实例化孩子集合
                    childrenTempList.add(chirldrenTreeNode);//添加子节点到集合里面
                    parentTreeNode.setChildren(childrenTempList);//设置孩子集合
                }else{//不空，说明之前就有孩子集合，设置过，可以直接遍历
                    children.add(chirldrenTreeNode);//添加子节点到集合里面
                }
                //自己调用自己,找孩子
                bindChirldren(chirldrenTreeNode,powerAllList);
            }
        }
    }

    /**
     * 得到所有树节点
     * @return
     */
    @Override
    public List<TreeNode> getAllTree() {
        //去除IDEA报黄色/灰色的重复代码的下划波浪线
        //setting 里面 搜索 inspections -->General -->Duplicated Code 取消勾选.提交之后就可以了.
        //得到不同用户所能看到的所有节点
        List<TreeNode> powerAllList = powerDao.getAllTree();

        //临时的powerList
        List<TreeNode> powerTempList = new ArrayList<TreeNode>();
        //判断是否为空
        if(powerAllList!=null&&powerAllList.size()>0){
            for(TreeNode ptreeNode:powerAllList){
                if(ptreeNode.getParentId()==0){//如果等于0,说明是一级节点，将所有的父节点先加入到树里
                    powerTempList.add(ptreeNode);
                    //在递归绑定子节点，慢慢将树都加入
                    bindChirldren(ptreeNode,powerAllList);
                }
            }
        }


            return powerTempList;
    }

    /**
     * 添加
     * @param map
     * @return
     */
    @Override
    public int add(Map map) {
        return powerDao.add(map);
    }

    /**
     * 更新
     * @param map
     * @return
     */
    @Override
    public int upd(Map map) {
        return powerDao.upd(map);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public int del(Integer id) {
        return powerDao.del(id);
    }

    /**
     * 根据id获取对应树节点
     * @return
     */
    @Override
    public List<Map> getTreeById(Integer id) {
        return powerDao.getTreeById(id);
    }

}
