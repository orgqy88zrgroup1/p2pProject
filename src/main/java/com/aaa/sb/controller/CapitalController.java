package com.aaa.sb.controller;

import com.aaa.sb.service.CapitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * className:CapitalController
 * discription:
 * author:ZXL
 */
@Controller
@RequestMapping("/capital")
public class CapitalController {

    @Autowired
    private CapitalService capitalService;

    /**
     * 跳转资金列表页面
     * @return
     */
    @RequestMapping("/toList")
    public String toList(){
        return "moneys/capital";
    }
    /**
     * 资金分页
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/page")
    public Object list(@RequestBody Map map){
        System.out.println(map);
        Map resultMap=new HashMap();
        resultMap.put("pageData",capitalService.getparam(map));
        resultMap.put("total",capitalService.getCount(map));
        return resultMap;
    }

    /**
     * 跳转资金列表页面
     * @return
     */
    @RequestMapping("/toLists")
    public String toLists(){
        return "moneys/invest";
    }
    /**
     * 资金分页
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/pages")
    public Object lists(@RequestBody Map map){
        System.out.println(map);
        Map resultMap=new HashMap();
        resultMap.put("pageData",capitalService.getparams(map));
        resultMap.put("total",capitalService.getCounts(map));
        return resultMap;
    }
}
