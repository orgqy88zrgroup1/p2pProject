package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * className:StatisticsDao
 * discription:
 * author:wzb
 * createTime:2018-12-24 11:31
 */
/*@CacheNamespace(implementation = RedisCache.class)*/
public interface StatisticsDao {

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
    @Select("select bi.id as bid,bs.bidAmount,ui.userName as username,bi.bidRate,bs.bidRate/bi.bidDeadline as monprofit from bid_submit bs left join bid_info bi on bi.id = bs.bidInfoID left join user_info ui on ui.userID = bs.userID where bi.id = #{id}")
    List<Map> getEChartsData(int id);


    /**
     * 查询所有标id
     * @return
     */
    @Select("select id from bid_info")
    List<Map> selBid();

    /**
     * 用户省份分布
     * @return
     */
    @Select("select count(id) as num,address from REALNAME_CERTIFICATION group by address")
    List<Map> selAddrssAndNum();


    /**
     * 得到标信息表的用户需求  1 2 3
     * @return
     */
    @Select("select bidtype from bid_info group by bidtype")
    List<Map> selBidType();

    /**
     * 得到标信息表的用户需求数据
     * data [{value: 30, name: '车贷'}]
     * @return
     */
    @Select("select (ROUND((select count(*) from bid_info where bidtype = #{demand}) / (select count(*) from bid_info ),2))*100 as a from dual")
    int selBidTypeData(int demand);




}
