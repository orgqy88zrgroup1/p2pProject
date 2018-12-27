package com.aaa.sb.controller;

import com.aaa.sb.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * className:DeptController
 * discription:部门控制层
 * author:wzb
 * createTime:2018-12-08 23:57
 */
@Controller
@RequestMapping("dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 查询部门列表
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public Object list(){
        return deptService.getDeptList();
    }

    /**
     * 跳转部门分页列表
     * @return
     */
    @RequestMapping("toList")
    public String toPage(){
        return "dept/deptList";
    }

    /**
     * 部门分页 mybatis
     * @return
     */
    @ResponseBody
    @RequestMapping("myPage")
    public Object page(@RequestBody Map map){
        System.out.println(map);
        return deptService.getDeptPage(map);
    }

    /**
     * 添加
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public Object addDept(@RequestBody Map map){
        System.out.println(map);
        return deptService.addDept(map);
    }


    /**
     * 修改
     * @return
     */
    @ResponseBody
    @RequestMapping("upd")
    public Object updDept(@RequestBody Map map){
        //System.out.println(map);
        return deptService.updDept(map);

    }

    /**
     * 删除
     * @return
     */
    @ResponseBody
    @RequestMapping("del")
    public Object delDept(int id){
        System.out.println(id);
        return deptService.delDept(id);

    }

    /**
     * 批量删除
     * @return
     */
    @ResponseBody
    @RequestMapping("/batchDel")
    public Object batchDel(String ids){
        System.out.println(ids);
        return deptService.batchDelete(ids);


    }

    /**
     * 部门启用或禁用
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("updState")
    public Object updState(@RequestBody Map map){
        System.out.println(map);
        return deptService.updDeptState(map);
    }


}
