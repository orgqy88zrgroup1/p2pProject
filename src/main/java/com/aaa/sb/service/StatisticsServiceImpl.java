package com.aaa.sb.service;

import com.aaa.sb.dao.StatisticsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * className:StatisticsServiceImpl
 * discription:
 * author:wzb
 * createTime:2018-12-24 11:41
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsDao statisticsDao;

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
    @Override
    public List<Map> getEChartsData(int id) {
        return statisticsDao.getEChartsData(id);
    }

    /**
     * 查询标id
     * @return
     */
    @Override
    public List<Map> selBid() {
        return statisticsDao.selBid();
    }

    /**
     * 用户省份分布
     * @return
     */
    @Override
    public List<Map> selAddrssAndNum() {
        List<Map> list = statisticsDao.selAddrssAndNum();
        //System.out.println(list);
        for (Map map : list) {
            //substring(1,5) 字符串下标从0开始 >=1 <5
            map.put("ADDRESS",(map.get("ADDRESS") + "").substring(0,2));
        }
        //System.out.println(list);
        return list;
    }
}
