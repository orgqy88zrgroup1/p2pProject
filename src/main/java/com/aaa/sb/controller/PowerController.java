package com.aaa.sb.controller;

import com.aaa.sb.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
