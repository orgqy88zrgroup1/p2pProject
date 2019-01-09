package com.aaa.sb.service;

import java.util.List;
import java.util.Map;

/**
 * className:BidService
 * discription:
 * author:gwd
 * createTime:2018-12-24 14:01
 */

public interface BidService {

    /**
     * 通过标id查找该标的信息
     * @return
     */
    Map getBidInfoById(int id);

    /**
     * 根据用户id查找该用户的信息
     * @param
     * @return
     */
    Map getBaseInfoByUserId();

    /**
     * 将投标信息放入到bid_submit投资记录表中
     * @param map
     * @return
     */
    int investBid(Map map);

    /**
     * 根据标的id来查投该标的总钱数
     * @param map
     * @return
     */
    Map findInvestMoney(Map map);

    /**
     * 根据用户id查找用户的登录信息
     * @return
     */
    List<Map> investItem(int id);

    /**
     * 根据招标人的id查找该人的还款信息
     * @param bidUserId
     * @return
     */
    List<Map> findRepayByBidUserId(int bidUserId);

    /**
     * 根据登录人id查找用户的支付密码
     * @return
     */
    int getPayPwd(Map map);

    /**
     * 校验投资钱数是否小于账户总金额
     * @param map
     * @return
     */
    int checkMoney(Map map);
}
