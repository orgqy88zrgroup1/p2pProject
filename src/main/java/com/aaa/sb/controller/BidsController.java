package com.aaa.sb.controller;

import com.aaa.sb.service.BidsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * className:BidsController
 * discription:
 * author:ZXL
 */
@Controller
@RequestMapping("/bids")
public class BidsController {
    @Autowired
    private BidsService bidsService;
    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping("/toList")
    public String toList(){
        return "bids/bidsaudit";
    }

    /**
     * 分页
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/page")
    public Object list(@RequestBody Map map){
        Map resultMap=new HashMap();
        resultMap.put("pageData",bidsService.getPageByParam(map));
        resultMap.put("total",bidsService.getPageCount(map));
        return resultMap;
    }

    /**
     * 更新审核表
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public Object update(@RequestBody Map map ){
        System.out.println(map);
        if(map.get("AUDITRESULT").equals("审核通过")){
            int update = bidsService.update(map);
            int upd=bidsService.upd(map);
            if(update==1&&upd==1){
                return 1;
            }else {
                return 0;
            }
        }else  {
            int update = bidsService.update(map);
            int upda=bidsService.upda(map);
            if(update==1&&upda==1){
                return 1;
            }else {
                return 0;
            }
        }

    }
}
