package com.aaa.sb.service;

import java.util.List;
import java.util.Map;

/**
 * className:StatisticsService
 * discription:
 * author:wzb
 * createTime:2018-12-24 11:40
 */
public interface StatisticsService {
    /**
     * 月盈利统计 一个标id对应多个投资用户 x轴显示用户名 y轴显示投资金额
     * 根据标id查询
     * 投标金额 bs.bidAmount
     * 用户名 ui.userName
     * 月利率 bi.bidRate
     * 月利息=投标利息/标借贷月份 bs.bidRate/bi.bidDeadline
     * @param id
     * @return
     */
    List<Map> getEChartsData(int id);

    /**
     * 查询所有标id
     * @return
     */
    List<Map> selBid();

    /**
     * 用户省份分布
     * @return
     */
    List<Map> selAddrssAndNum();

    /**
     * 用户需求
     * @return
     */
    List<Map> selDemandData();
}
