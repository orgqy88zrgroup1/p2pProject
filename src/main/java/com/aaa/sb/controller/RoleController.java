package com.aaa.sb.controller;

import com.aaa.sb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * className:RoleController
 * discription:
 * author:wzb
 * createTime:2018-12-10 18:37
 */
@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("list")
    public Object list(){
        return roleService.getRoleList();
    }

    /**
     * 跳转角色列表页面
     * @return
     */
    @RequestMapping("toList")
    public String toList(){
        return "role/roleList";
    }

    /**
     * 分页查询
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("myPage")
    public Object myPage(@RequestBody Map map){
        System.out.println(map);
        return roleService.getRolePage(map);
    }

    /**
     * 增加
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public Object addRole(@RequestBody Map map){
        System.out.println(map);
        //return null;
        return roleService.addRole(map);
    }

    /**
     * 修改
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("upd")
    public Object updRole(@RequestBody Map map){
        System.out.println(map);
        //return null;
        return roleService.updRole(map);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("del")
    public Object delRole(int id){
        System.out.println(id);
        //return null;
        return roleService.delRole(id);
    }

    /**
     * 批量增加
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("addRoleIdAndPowerId")
    public Object addRoleIdAndPowerId(@RequestBody Map map){
        //System.out.println(map);
        //System.out.println(map.get("roleList"));
        //return null;
        return roleService.batchAdd(map);
    }

    /**
     * 根据roleid查询tb_power表具体的数据
     * @return
     */
    @ResponseBody
    @RequestMapping("selRoleIdAndPowerId")
    public Object selRoleIdAndPowerId(@RequestBody Map map){
        System.out.println(map);
        return roleService.selRoleIdAndPowerId(map);
    }




}
