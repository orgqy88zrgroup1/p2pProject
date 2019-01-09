package com.aaa.sb.controller;

import com.aaa.sb.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * className:BidController
 * discription:
 * author:gwd
 * createTime:2018-12-24 08:42
 */
@Controller
@RequestMapping("/bid")
public class BidController {
    
    @Autowired
    private BidService bidService;

    /**
     * 携带信息跳转到用户投标页面
     * @return
     */
    @RequestMapping("/getBidInfo")
    /*public Object getBidInfo(@RequestBody Map map, Model model,int id){*/
    public Object getBidInfo( Model model,int id){
        /*Integer id = Integer.valueOf(map.get("id") + "");*/
        /*Integer userId = Integer.valueOf(map.get("userId") + "");*/
        System.out.println(id);
        //通过标的id查找该标的信息
        Map bidInfo = bidService.getBidInfoById(id);
        //根据用户id查找该用户的信息
        Map realInfo = bidService.getBaseInfoByUserId();
        //获得投资列表
        List<Map> investItem = bidService.investItem(id);
        //得到还款列表
        List<Map> repayListMap = bidService.findRepayByBidUserId(id);
        System.out.println("repayListMap:"+repayListMap);
        //如果投资列表不为空 就带到页面
        if(investItem!=null&&investItem.size()>0){
            model.addAttribute("investItem",investItem);
        }
        if(repayListMap!=null&&repayListMap.size()>0){
            model.addAttribute("repayListMap",repayListMap);
        }
        System.out.println(bidInfo);
        System.out.println(realInfo);
        System.out.println(investItem);
        if(bidInfo!=null&&bidInfo.size()>0&&realInfo!=null&&realInfo.size()>0){
            model.addAttribute("bidInfo",bidInfo);
            model.addAttribute("realInfo",realInfo);
            System.out.println(model);
            return "bid/bidInfo";
        }else {
            System.out.println("找不到该标的信息");
            return null;
        }
    }

    /**
     * 用户投标
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/invest")
    public int investBid(@RequestBody Map map){
        System.out.println(map);
        int i = bidService.investBid(map);
        return i;
    }

    /**
     * 获得当前总投资钱数
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/currTotalMoney")
    public int currTotalMoney(@RequestBody Map map){
        System.out.println(map);
        //获得该标的所有投资金额
        Map investMoney = bidService.findInvestMoney(map);
        System.out.println("investMoney："+investMoney);
        if (investMoney!=null&&map.size()>0){
            //得到总投资钱数
            int bidamount = Integer.valueOf(investMoney.get("BIDAMOUNT") + "");
            System.out.println("总投资钱数："+bidamount);
            return bidamount;
        }
        return -1;
    }

    /**
     * 校验投资时的支付密码
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkPayPwd")
    public int checkPayPwd(@RequestBody Map map){
        System.out.println(map);
        int i = bidService.getPayPwd(map);
        if(i==1){
            //说明密码正确
            System.out.println("密码正确");
            return 1;
        }
        return 0;
    }

    /**
     * 校验投资钱数是否小于账户总金额
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkMoney")
    public int checkMoney(@RequestBody Map map){
        int i = bidService.checkMoney(map);
        if(i==1){
            return 1;
        }
        return 0;
    }

}
