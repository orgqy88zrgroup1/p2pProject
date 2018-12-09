package com.aaa.sb.entity;

import java.util.List;

/**
 * className:TreeNode
 * discription:
 * author:wzb
 * createTime:2018-12-09 22:36
 */
public class TreeNode {

    private int id;
    private String label;
    private int parentId;
    private String state;
    private String iconCls;
    private String url;
    //因为要选择树节点
    private String checked;
    private List<TreeNode> children;

    public TreeNode() {
    }

    public TreeNode(int id, String label, int parentId, String state, String iconCls, String url) {
        this.id = id;
        this.label = label;
        this.parentId = parentId;
        this.state = state;
        this.iconCls = iconCls;
        this.url = url;
    }

    public TreeNode(int id, String label, int parentId, String state, String iconCls, String url, String checked) {
        this.id = id;
        this.label = label;
        this.parentId = parentId;
        this.state = state;
        this.iconCls = iconCls;
        this.url = url;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
