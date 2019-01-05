package com.aaa.sb.service;



import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * className:Bid_two_auditService
 * discription:
 * author:wuyanle
 * createTime:2018-12-20 10:41
 */
public interface Bid_two_auditService {
    /**
     * 满标二审查询
     * @return
     */
    Map getbidList(Map map);
    /**
     * 用户账户标查询
     * @param map
     * @return
     */

    List<Map> gatList(Map map);

    /**
     * 用户可用余额更新1
     * @param map
     * @return
     */
    int updatejiaqian(Map map);
    /**
     * 满标二审通过
     * @param map
     * @return
     */
    int updatetongguo(Map map);
    /**
     * 满标二审驳回
     * @param map
     * @return
     */
    int updatebohui(Map map);

    /**
     * 查询历史审核记录
     * @param map
     * @return
     */
   Map getaudit(Map map);

    /**
     * 还款计划封装 定期还款 一次付清
     * @return
     */
    //int getLoopBack(double benXi,int bid,Date date22,double monMoney,int bidRepayState,int biddeadline, int bidRepayMethod,int userid);

    /**
     * 还款计划封装 定期还息 到期还本
     * @return
     */
    //int getLoopBack2(double benXi,int bid,Date date22,double monMoney,int bidRepayState,int biddeadline, int bidRepayMethod,int userid,double monEndBenXi, double monLixi);


}
