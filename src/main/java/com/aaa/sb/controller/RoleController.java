package com.aaa.sb.controller;

import com.aaa.sb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
