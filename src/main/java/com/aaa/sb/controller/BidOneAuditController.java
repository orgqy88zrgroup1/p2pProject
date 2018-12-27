package com.aaa.sb.controller;

import com.aaa.sb.service.Bid_one_auditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Map;

/**
 * className:BidOneAuditController
 * discription:
 * author:wuyanle
 * createTime:2018-12-18 16:53
 */
@Controller
@RequestMapping("/oneaudit")
public class BidOneAuditController {
    @Autowired
    private Bid_one_auditService bidOneAuditService;

    /**
     * 审核页面跳转
     * @return
     */
    @RequestMapping("/toPage")
    public String page(){
        return "audit/oneaudit";
    }

    /**
     * 审核标分页查询
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public Object getList(@RequestBody Map map){

        return bidOneAuditService.getbidList(map);
    }

    /**
     *审核通过
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public Object update(@RequestBody Map map){
        return bidOneAuditService.updatetongguo(map);
    }

    /**
     * 审核驳回
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/toUpdate")
    public int toUpdate(@RequestBody Map map){
        return bidOneAuditService.updatebohui(map);
    }

}
