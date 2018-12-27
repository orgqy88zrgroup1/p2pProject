package com.aaa.sb.service;



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
     * 用户可用余额更新
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
}
