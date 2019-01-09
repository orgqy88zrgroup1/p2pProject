package com.aaa.sb.controller;

import com.aaa.sb.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * className:StatisticsController
 * discription:
 * author:wzb
 * createTime:2018-12-24 11:29
 */
@Controller
@RequestMapping("stat")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 跳转月盈利图示页面
     * @return
     */
    @RequestMapping("toMonView")
    public String toMonView(){
        return "statistics/monProfit";
    }

    /**
     * 月盈利统计
     * @param id
     * @return List<Map>
     */
    @ResponseBody
    @RequestMapping("/monProfit")
    public Object getMonProfitData(Integer id){
        //System.out.println(id);
        //System.out.println(statisticsService.getEChartsData(id));
        return statisticsService.getEChartsData(id);
    }


    /**
     * 查询所有标id
     * @return List<Map>
     */
    @ResponseBody
    @RequestMapping("/selBid")
    public Object selBid(){
        //System.out.println(statisticsService.selBid());
        return statisticsService.selBid();
    }

    /**
     * 导出excel
     * @return
     */
    @RequestMapping("/monProfitExcel")
    public String echartDataExp(Model model, Integer id, HttpServletResponse response){
        model.addAttribute("monExcelList",  statisticsService.getEChartsData(id));
        //设置返回值类型 MIME 导出excel
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment;filename=monProfit.xls");//指定下载的文件名
        //Map<String, Object> x = model.asMap();
        //System.out.println(x);
        return "statistics/monExcel";
    }


    /**
     * 跳转用户省份分布图示页面
     * @return
     */
    @RequestMapping("toPerView")
    public String toPerView(){
        return "statistics/userProfit";
    }

    /**
     * 用户省份分布
     * @return List<Map>
     */
    @ResponseBody
    @RequestMapping("/selAddress")
    public Object selAddress(){
        //System.out.println(statisticsService.selBid());
        return statisticsService.selAddrssAndNum();
    }


}
