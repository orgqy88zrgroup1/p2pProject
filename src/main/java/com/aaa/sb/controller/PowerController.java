package com.aaa.sb.controller;

import com.aaa.sb.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * className:PowerController
 * discription:
 * author:wzb
 * createTime:2018-12-09 22:15
 */
@Controller
@RequestMapping("power")
public class PowerController {

    @Autowired
    private PowerService powerService;

    /**
     * 权限树
     * @return
     */
    @ResponseBody
    @RequestMapping("tree")
    public Object getPoserTree(){
        return powerService.getPowerEndTree();
    }

    /**
     * 所有树节点
     * @return
     */
    @ResponseBody
    @RequestMapping("allTree")
    public Object getAllTree(){
        return powerService.getAllTree();
    }

    /**
     * 根据id获取对应树节点信息(没用)
     * @return
     */
    @ResponseBody
    @RequestMapping("idTree")
    public Object getTreeById(Integer id,@RequestBody Map map){
        System.out.println(id);
        System.out.println(map);
        return powerService.getTreeById(id);
    }

    /**
     * 跳转权限列表页面
     * @return
     */
    @RequestMapping("toList")
    public String toList(){
        return "power/powerList";
    }


    /**
     * 添加节点
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public Object addTreeNode(@RequestBody Map map){
        return powerService.add(map);
    }



    /**
     * 更新节点
     * @return
     */
    @ResponseBody
    @RequestMapping("upd")
    public Object updTreeNode(@RequestBody Map map){
        System.out.println(map);
        return powerService.upd(map);
    }

    /**
     * 删除节点
     * @return
     */
    @ResponseBody
    @RequestMapping("del")
    public Object delTreeNode(Integer id){
        return powerService.del(id);
    }



}
