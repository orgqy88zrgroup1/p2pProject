package com.aaa.sb.controller;



import com.aaa.sb.service.Bid_two_auditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * className:BidTwoAuditController
 * discription:
 * author:wuyanle
 * createTime:2018-12-20 10:48
 */
@Controller
@RequestMapping("twoaudit")
public class BidTwoAuditController {
    @Autowired
    private Bid_two_auditService bidTwoAuditService;

    /**
     * 审核页面跳转1
     * @return
     */
    @RequestMapping("/toPage")
    public String page(){
        return "audit/twoaudit";
    }

    /**
     * 审核标分页查询
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public Object getList(@RequestBody Map map){
        return bidTwoAuditService.getbidList(map);
    }

    /**
     *审核通过
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public int update(@RequestBody Map map){
        System.out.println(map+".....................");
        return bidTwoAuditService.updatetongguo(map);
    }

    /**
     * 审核驳回
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/toUpdate")
    public int toUpdate(@RequestBody Map map){
        return bidTwoAuditService.updatebohui(map);
    }
    /**
     * 审核记录页面跳转
     * @return
     */
    @RequestMapping("/record")
    public String topage(){
        return "audit/recordaudit";
    }

    /**
     * 历史审核记录
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/recordaudit")
    public Object getaudit(@RequestBody Map map){
        return bidTwoAuditService.getaudit(map);

    }
}
