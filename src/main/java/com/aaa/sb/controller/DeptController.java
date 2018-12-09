package com.aaa.sb.controller;

import com.aaa.sb.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * className:DeptController
 * discription:
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

}
